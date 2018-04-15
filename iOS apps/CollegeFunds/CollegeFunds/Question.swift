//
//  Question.swift
//  CollegeCalculator
//
//  Created by Neha Nish on 11/8/17.
//  Copyright © 2017 Neha Nishikant. All rights reserved.
//

import Foundation

class Question{
    var question=""
    var choice1: Choice?
    var choice2: Choice?
    var choice3: Choice?
    var choice4: Choice?
    
    init (question: String, choice1: Choice, choice2: Choice, choice3: Choice, choice4: Choice){
        self.question=question;
        self.choice1=choice1;
        self.choice2=choice2;
        self.choice3=choice3;
        self.choice4=choice4;
    }
}
