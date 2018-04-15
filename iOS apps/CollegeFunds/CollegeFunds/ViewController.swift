//
//  ViewController.swift
//  CollegeFunds
//
//  Created by Neha Nish on 10/31/17.
//  Copyright © 2017 Neha Nish. All rights reserved.
//

import UIKit

var label: UILabel?
var buttonNext: UIButton?
var buttonA: UIButton?
var buttonB: UIButton?
var buttonC: UIButton?
var buttonD: UIButton?
var totalCost: Double=0
var monthlyLoan: Double=0

class ViewController: UIViewController {
    
    private var brain: CollegeCalcBrain = CollegeCalcBrain() //new calc brain
    
    override func viewDidLoad(){
        super.viewDidLoad()
        setView()
    }
    
    func setView(){
        label = self.view.viewWithTag(5) as? UILabel
        buttonNext=self.view.viewWithTag(0) as? UIButton
        buttonA = self.view.viewWithTag(1) as? UIButton
        buttonB = self.view.viewWithTag(2) as? UIButton
        buttonC = self.view.viewWithTag(3) as? UIButton
        buttonD = self.view.viewWithTag(4) as? UIButton
        loadQuestion(questionNum: 1)
    }

    @IBAction func clickChoice(_ sender: UIButton) {
        for i in 1...4{
            if let button = self.view.viewWithTag(i) as? UIButton{
                button.backgroundColor=UIColor.white
            }
        }
        sender.backgroundColor=UIColor.blue
        brain.currentChoice=sender.tag
    }
    
    @IBAction func nextQuestion(_ sender: UIButton) {
        if (brain.currentQuestion<=brain.questions.count){
            var choice: Choice?
            switch brain.currentChoice{
                case 1: choice=brain.questions[brain.currentQuestion-1].choice1
                case 2: choice=brain.questions[brain.currentQuestion-1].choice2
                case 3: choice=brain.questions[brain.currentQuestion-1].choice3
                case 4: choice=brain.questions[brain.currentQuestion-1].choice4
                default: print("error")
            }
            print(choice!.description)
            brain.logChoices(tag: brain.currentChoice)
        }
        brain.currentQuestion+=1
        if (brain.currentQuestion<=brain.questions.count){
            loadQuestion(questionNum: brain.currentQuestion)
        } else if (brain.currentQuestion==brain.questions.count+1){
            label!.text="$"
            for i in 1...4{
                if let button = self.view.viewWithTag(i) as? UIButton{
                    button.isHidden=true
                }
            }
            
            var r=brain.computeAnswer()
            totalCost=r[0]
            monthlyLoan=r[1]
            label!.text="total cost: "+String(totalCost)+"; monthly loan: "+String(monthlyLoan)
        } else {
            buttonA!.setTitle("Restart", for: UIControlState.normal)
            brain.reset()
            loadQuestion(questionNum: 1)
        }
    }
    
    func loadQuestion(questionNum: Int){
        for i in 1...4{
            if let button = self.view.viewWithTag(i) as? UIButton{
                button.backgroundColor=UIColor.white
            }
        }
        
        label!.text=brain.questions[questionNum-1].question
        if (brain.questions[questionNum-1].choice1!.show){
            buttonA!.isHidden=false
            buttonA!.setTitle(brain.questions[questionNum-1].choice1!.label, for: UIControlState.normal)
        } else {buttonA!.isHidden=true}
        if (brain.questions[questionNum-1].choice2!.show){
            buttonB!.isHidden=false
            buttonB!.setTitle(brain.questions[questionNum-1].choice2!.label, for: UIControlState.normal)
        } else {buttonB!.isHidden=true}
        if (brain.questions[questionNum-1].choice3!.show){
            buttonC!.isHidden=false
            buttonC!.setTitle(brain.questions[questionNum-1].choice3!.label, for: UIControlState.normal)
        } else {buttonC!.isHidden=true}
        if (brain.questions[questionNum-1].choice4!.show){
            buttonD!.isHidden=false
            buttonD!.setTitle(brain.questions[questionNum-1].choice4!.label, for: UIControlState.normal)
        } else {buttonD!.isHidden=true}
    }
}

