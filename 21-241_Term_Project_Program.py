################################################################################
##############              21-241 Term Project                   ##############
##############                Topic: Page Rank                    ##############
##############        andrewIDs: nnishika and kratij              ##############
##############                                                    ##############
##############       Sections:                                    ##############
##############   1. Setup                                         ##############
##############   2. Method 1: Using eigenvectors                  ##############
##############   3. Method 2: Using iteration to find convergence ##############
##############   3. Helper Functions alphabetically arranged      ##############
##############   4. Testing the Program                           ##############
################################################################################

################################################################################
# The primary function which the user must call is rank(markov) which takes in a
# markov chain called 'markov' which is a list of lists (a 2D list) and returns
# a tuple of two lists with pages in descending order of 'importance'. The first
# list corresponds to the ranking from method 1 and the second to the ranking
# from method 2.
################################################################################

################################################################################
############                         Setup                        ##############
################################################################################

def rank(markov):
    # input: markov chain: a list of list that maps pages (index of inner
    # lists) to destinations (inner lists)
    # output: a tuple with two lists each of which is a list with pages in
    # descending order of 'importance'. The first list corresponds to the
    # ranking from method 1 and the second to the ranking from method 2.
    # epsilon that defines 'almost the same' to identify convergence
    epsilon = 0.000000000001
    # damp Weight
    dampWt = 0.15
    # fixing end nodes of the input markov chain
    fixEndNodes(markov)
    # method 1: using eigenvectors
    ans1 = limitToRank(limitVetor1(markov, dampWt))
    # method 2: iterative multiplication until convergence
    ans2 = limitToRank(limitVector2(markov, epsilon, dampWt))
    return ans1, ans2

def fixEndNodes(markov):
    # fixes cases when one node in the graph has no outgoing edges
    # i.e., if one column is a zero vector, we replace it with all entries
    # having equal probability. This function makes the change in the markov
    # chain itself because it is easier to check for an element of the chain
    # being an empty list than a column in a matrix being the zero vector
    for listOfDestinations in markov:
        if len(listOfDestinations) == 0:
            for page in range(len(markov)):
                listOfDestinations.append(page)

def limitToRank(limitVector):
    # given a limit vector which lists the probabilities of being
    # at any page in the form limitVector[i] is the probability of being on
    # page i, this function returns a ranking of the pages in descending order
    # of probability
    pagesWithProbability = {}
    for page in range(len(limitVector)):
        pagesWithProbability[page] = limitVector[page]
        # creates a new dictionary assignment: page --> probability
    rank = []
    while len(pagesWithProbability) > 0:
        maxPage = maxFromDictionary(pagesWithProbability)
        rank.append(maxPage)
        pagesWithProbability.pop(maxPage)
    return rank

################################################################################
############             Method 1: Using eigenvectors             ##############
################################################################################

def limitVector1(markov, dampWt):
    # this function outputs the limit vector according to method 1
    evecs = findEvecs(createPageRankMatrixWithDamping(markov, dampWt))
    for vector in evecs:
    # finds the first and only vector that can be scaled to a probability vector
        if isLimitVector(vector):
            convertToPositive(vector)
            return vector

def isLimitVector(vector):
    # function checks if given vector has all positive / all negative entries
    isPositive = None
    for entry in vector:
        if isPositive == None: # this checks if we are on the first entry
            if entry < 0:
                isPositive = False
            elif entry > 0:
                isPositive = True # sets to + or - based on the first entry
        else:
            if entry < 0 and isPositive or entry > 0 and not isPositive:
                return False # if future entry doesn't match sign of first entry
    return True

def createPageRankMatrixWithDamping(markov, dampWt):
    # adds the markov transition matrix and damping matrix according to input
    # damp weight, dampWt
    P0 = createPageRankMatrix(markov)
    B = createDampingMatrix(markov)
    # initialize a zero matrix of same dimensions as A and B
    P = [[0]*len(P0) for i in range(len(P0))]
    # taking an element wise weighted average of A and B based on dampWt
    for i in range(len(P0)):
        for j in range(len(P)):
            P[i][j] = (1 - dampWt) * P0[i][j] + dampWt * B[i][j]
    return P

def createPageRankMatrix(markov):
    # creates a markov transition matrix corresponding to the given markov chain
    m = len(markov)
    P0 = [[0]*m for index in range(m)]
    for page in range(m):
        denominator = len(markov[page])
        for destination in range(m):
            if destination in markov[page]:
                P0[destination][page] = 1/denominator
            else:
                P0[destination][page] = 0
    return P0

def createDampingMatrix(markov):
    # creates a transition matrix corresponding to a markov chain of the same
    # length as the given markov chain, where all nodes point to all nodes
    # (including self)
    m = len(markov)
    B = [[1/m]*m for index in range(m)]
    return B

################################################################################
###########      Method 2: Using iteration to find convergence       ###########
################################################################################

def limitVector2(markov, epsilon, dampWt):
    # returns the limit vector according to method 2
    markovTranspose, denominators = findMarkovTranspose(markov)
    vec0 = startVector(markov)
    vecA = findNextVector(markovTranspose, denominators, vec0, dampWt)
    vecB = findNextVector(markovTranspose, denominators, vecA, dampWt)
    while not withinEpsilon(vecA, vecB, epsilon):
        vecA = vecB
        vecB = findNextVector(markovTranspose, denominators, vecA, dampWt)
    return vecB

def findMarkovTranspose(markov):
    # given markov, returns markovTranspose as explained in the paper
    markovTranspose = [[] for i in range(len(markov))]
    denominators = []
    for j in range(len(markov)):
        for i in markov[j]:
            markovTranspose[i].append(j)
        denominators.append(len(markov[j]))
    return markovTranspose, denominators

def findNextVector(markovTranspose, denominators, currentVector, dampWt):
    # given the current vector and information derived from markov,
    # this functions returns the next vector (the probabilities at the next
    # step of the random walk)
    nextVector = []
    m = len(currentVector) # now m is the number of pages
    for i in range(m): # go through all pages in markovTranspose
        newPageProbability = 0
        for j in markovTranspose[i]:
            newPageProbability += (1 - dampWt) * currentVector[j]/denominators[j]
        # now we add the damping matrix part. The value of the damping matrix
        # times a probability vector is a constant: 1/m where m is the number
        # of pages
        newPageProbability += dampWt/m
        nextVector.append(newPageProbability)
    return nextVector

################################################################################
###############     Helper Functions alphabetically arranged     ###############
################################################################################

def convertToPositive(vector):
    # returns a vector where every element is the absolute value of the
    # corresponding element in the input vector
    for i in range(len(vector)):
        vector[i] = abs(vector[i])

import numpy
def findEvecs(matrix):
    # returns a list of eigen vectors of a matrix A
    return transpose(numpy.linalg.eig(matrix)[1].real.tolist())
    # .tolist() converts the numpy array to a 2D list and the transpose function
    # is used because the eigenvectors form the columns not the rows of the
    # returned matrix. In a 2D list representation, we have a list of the rows
    # and so to have a list of the eigenvectors it must be the case that the
    # eigenvectors are the rows and not the columns of the matrix.

def maxFromDictionary(dictionary):
    # takes in a dictionary that maps keys to values and returns the key in the
    # dictionary which is associated with the maximum value
    # this is different from a standard python max function because here we
    # want to return the key associated with the max value and not the max key
    maxKey = None
    maxValue = 0
    for key in dictionary:
        if maxKey == None or maxValue < dictionary[key]:
            maxKey = key
            maxValue = dictionary[key]
    return maxKey

def startVector(markov):
    # calculates a start vector for a given markov chain: where all pages have
    # equal probability
    denominator = len(markov)
    v = [1/denominator] * len(markov)
    return v

def transpose(matrix):
    # returns the transpose of the input matrix
    matrixTranspose = [[0]*len(matrix) for i in range(len(matrix))]
    for i in range(len(matrix)):
        for j in range(len(matrix)):
            matrixTranspose[j][i] = matrix[i][j]
    return matrixTranspose

def withinEpsilon(vec1, vec2, epsilon):
    # checks if two vectors are 'almost' equal to each other by seeing if each
    # element of vector 1 is within epsilon distance from the corresponding
    # element of vector 2 for a given epsilon
    assert(len(vec1) == len(vec2))
    for i in range(len(vec1)):
        if not -epsilon <= vec1[i] - vec2[i] <= epsilon:
            return False
    return True

################################################################################
###############              Testing the Program             ###################
################################################################################

def testPageRankProgram():
    # the results expected are in the form of a 2-tuple because we return
    # answers from both methods

    # test case 1
    markov = [[1, 3, 4], [0, 2, 3], [1, 2], [1, 3, 4], [0]]
    assert(rank(markov) == ([0, 1, 3, 2, 4], [0, 1, 3, 2, 4]))

    # test case 2
    markov = [[1], [2], [1]]
    assert(rank(markov) == ([1, 2, 0], [1, 2, 0]))

    # test case 3
    markov = [[1, 3, 4], [0, 2, 4], [3, 6], [2, 4, 6], [5, 8], [4, 6, 8],
                                    [0, 7, 9], [0, 6, 8], [2, 9], [0, 2, 8]]
    assert(rank(markov) == ([2, 6, 8, 0, 4, 3, 9, 5, 7, 1],
                                                [2, 6, 8, 0, 4, 3, 9, 5, 7, 1]))

    # test case 4
    markov = [[1, 5], [2, 5], [1, 3, 5], [4], [1, 5], [2, 6], [0, 1]]
    assert(rank(markov) == ([5, 2, 1, 6, 4, 3, 0], [5, 2, 1, 6, 4, 3, 0]))
    print('Yay!')

testPageRankProgram()

#### Credit:
#### Used numpy to find eigenvectors
