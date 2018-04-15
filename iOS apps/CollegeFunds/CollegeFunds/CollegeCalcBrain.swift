//
//  CollegeCalcBrain.swift
//  CollegeFunds
//
//  Created by Neha Nish on 10/31/17.
//  Copyright © 2017 Neha Nish. All rights reserved.
//

import Foundation

struct CollegeCalcBrain{
    
    var answers = [Int]()
    var questions = [Question]()
    var currentChoice=0
    var currentQuestion=1
    var ac=0.0 //annual cost
    var tc=0.0 //total cost
    var lc=0.0 //loan cost
    var mc=0.0 //monthly loan cost
    var results = [Double]()
    var q1: Question = Question(question: "What kind of university are you going to?", choice1: Choice(label: "Public in state", show: true, description: "15k"), choice2: Choice(label: "Public out of state", show: true, description: "30k"), choice3: Choice(label: "Private", show: true, description: "60k"), choice4: Choice(label: "D1", show: false, description: "N/A"))
    var q2: Question = Question(question: "What is your househod income?", choice1: Choice(label: "<100k", show: true, description: "free"), choice2: Choice(label: "100k-150k", show: true, description: "50% off"), choice3: Choice(label: "150k-250k", show: true, description: "25% off"), choice4: Choice(label: "250k+", show: true, description: "full pay"))
    var q3: Question = Question(question: "How much annual merit scholarship are you receiving?", choice1: Choice(label: "0k", show: true, description: "full pay"), choice2: Choice(label: "5k", show: true, description: "-5k"), choice3: Choice(label: "10k", show: true, description: "-10k"), choice4: Choice(label: "20k", show: true, description: "-20k"))
    var q4: Question = Question(question: "How many years do you plan on attending university?", choice1: Choice(label: "2 yrs", show: true, description: "*2"), choice2: Choice(label: "4 yrs", show: true, description: "*4"), choice3: Choice(label: "6 yrs", show: true, description: "*6"), choice4: Choice(label: "8 yrs", show: true, description: "*8"))
    var q5: Question = Question(question: "What percentage of the cost will you pay back in loans?", choice1: Choice(label: "25%", show: true, description: "loan=25%"), choice2: Choice(label: "50%", show: true, description: "loan=50%"), choice3: Choice(label: "75%", show: true, description: "loan=75%"), choice4: Choice(label: "100%", show: true, description: "full loan"))
    var q6: Question = Question(question: "What is the interest rate on your loan?", choice1: Choice(label: "3%", show: true, description: "*1.03"), choice2: Choice(label: "4%", show: true, description: "*1.04"), choice3: Choice(label: "5%", show: true, description: "*1.05"), choice4: Choice(label: "6%", show: true, description: "*1.06"))
    var q7: Question = Question(question: "In how many years will you pay your loans off?", choice1: Choice(label: "5", show: true, description: "/60"), choice2: Choice(label: "8", show: true, description: "/96"), choice3: Choice(label: "10", show: true, description: "/120"), choice4: Choice(label: "12", show: true, description: "/144"))
    
    init(){
        questions.append(q1)
        questions.append(q2)
        questions.append(q3)
        questions.append(q4)
        questions.append(q5)
        questions.append(q6)
        questions.append(q7)
    }
    
    enum function{
        case aToA((Double)-> Double) //annual to annual
        case aToT((Double)-> Double) //annual to total
        case tToT((Double)-> Double) //total to total
        case tToL((Double)-> Double) //total to loan
        case lToL((Double)-> Double) //loan to loan
        case lToM((Double)-> Double) //loan to monthly loan
    }
    
    private var operations: Dictionary<String, function> = /*table to hold constants for operation*/[
        "A1": function.aToA({$0+15000}), //public IS //input: 0, output: annual cost
        "B1": function.aToA({$0+30000}), //public OOS
        "C1": function.aToA({$0+60000}), //private
        "A2": function.aToA({$0*0}), //how much is your income 0-100k //input: annual cost, output: annual cost
        "B2": function.aToA({$0*0.5}), //100-150
        "C2": function.aToA({$0*0.75}), //150-250
        "D2": function.aToA({$0*1}), //250+
        "A3": function.aToA({$0-0}), //how much annual merit scholarship are you receiving?--> NOT FINANCIAL AID //input: annual cost, output: annual cost
        "B3": function.aToA({$0-5000}),
        "C3": function.aToA({$0-10000}),
        "D3": function.aToA({$0-20000}),
        "A4": function.aToT({$0*2}), //how many years //input: annual cost, output: total cost
        "B4": function.aToT({$0*4}),
        "C4": function.aToT({$0*6}),
        "D4": function.aToT({$0*8}),
        "A5": function.tToL({$0*0.25}), //how much loan, 25% //input: total cost, output: loan cost
        "B5": function.tToL({$0*0.50}),
        "C5": function.tToL({$0*0.75}),
        "D5": function.tToL({$0*1}),
        "A6": function.lToL({$0*1.03}), //interest rate? //input: loan cost, output: loan cost
        "B6": function.lToL({$0*1.04}),
        "C6": function.lToL({$0*1.05}),
        "D6": function.lToL({$0*1.06}),
        "A7": function.lToM({$0/60}), //how many years do you wanna pay your loans off in //input: loan cost, output: monthly loan
        "B7": function.lToM({$0/96}),
        "C7": function.lToM({$0/120}),
        "D7": function.lToM({$0/144})
    ]

    mutating func logChoices(tag: Int){
        answers.append(tag)
    }
    
    mutating func computeAnswer()-> Array<Double>{
        var c: String
        for i in 0...answers.count-1{
            switch answers[i]{
                case 1: c="A"
                case 2: c="B"
                case 3: c="C"
                case 4: c="D"
                default: c="E"
            }
            let s = c+String(i+1)
            if let operation=operations[s]{
                switch operation{
                case .aToA(let f): ac=f(ac); print(ac)
                    case .aToT(let f): tc=f(ac); print(tc)
                    case .tToT(let f): tc=f(tc); print(tc)
                    case .tToL(let f): lc=f(tc); print(lc)
                    case .lToL(let f): lc=f(lc); print(lc)
                    case .lToM(let f): mc=f(lc); print(mc)
                }
            } else {print("error: "+s)}
        }
        if (tc<0){
            tc=0
        }
        if (mc<0){
            mc=0
        }
        results.append(tc)
        results.append(mc)
        return results
    }
    
    mutating func reset (){
        answers.removeAll()
        currentChoice=0
        currentQuestion=1
        ac=0.0 //annual cost
        tc=0.0 //total cost
        lc=0.0 //loan cost
        mc=0.0 //monthly loan cost
        results.removeAll()
    }
}
