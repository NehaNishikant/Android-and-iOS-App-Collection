//
//  ViewController.swift
//  Calculator
//
//  Created by Neha Nishikant on 9/14/17.
//  Copyright © 2017 Neha Nishikant. All rights reserved.
//

import UIKit

class ViewController: UIViewController { //: inhertence //this class is the controller in mvc!
    var userIsInTheMiddleOfTyping=false; //must inititialize
    
    @IBOutlet weak var display: UILabel! //optionals automatically set to nil //instance var. connected to dsplay by pressing control + click on display UIwidget&drag //? for optional, ! for implicit unwrapping so that every time we use display it automatically unwraps
    
    var displayValue: Double{ //because we know the dislay will always be a double
        get{
            return Double(display.text!)!
        }
        set{
            display.text=String(newValue);
        }
    }
    
    @IBAction func touchDigit(_ sender: UIButton) { /*returns string*/
        //see? -> ex: drawHorizontalLines(from: 5.0, to: 8.5, using: UIColor.blue)
        //the reason it says "sender" with no external nameis because the name is implicit. it is rarely used
        
        //by copy-pasting the buttons on the xml, this method applies to all of them
        let digit = sender.currentTitle! //local var //no need to give type if its implicit //let instead of vaar because since the value of digit was never changed, its more like a constant. moe efficien fo swift to know which vars are immutable so it doesn't have to copy them every time //what's that ! is about? witout the exclamation point, digit is type "Sting?" aka Optional: It has two possible values, None and Some(T), where T is an associated value of the correct data type available in Swift. In this case "T" is the String.
        print("\(digit) was called")
        
        
        if (userIsInTheMiddleOfTyping){
            let textCurrentlyInDisplay = display.text!
            display.text = textCurrentlyInDisplay + digit //automatic unwrap
        } else {
            display.text=digit //this gets rid of whatever is already on the display before user types (ex: 0 from the start of the app, answer from previous operation...)
            userIsInTheMiddleOfTyping=true;
        }
    }

    
    /* example method
    func drawHorizontalLines(from startX: Double, to endX: Double, using color: UIColor){
        ": type" for each parameter
        startX, endX and UIColor are the internal names. These are the names you would use in this method.
        from, to, and using are the extrernal names you use when calling
        
    }*/
    
    private var brain: CalculatorBrain = CalculatorBrain() //new calc brain
    
    @IBAction func performOperation(_ sender: UIButton) {
        if (userIsInTheMiddleOfTyping){
            brain.setOperand(operand: displayValue)
            userIsInTheMiddleOfTyping=false; //removes past # from the screen
            
        }
        if let mathamticalSymbol = sender.currentTitle{
            brain.performOperation(symbol: mathamticalSymbol)
        }
        if let result = brain.result{ //if result, this new var, is able to be set into double, it shall equal brain.result
            displayValue = result
        }
    }
}
