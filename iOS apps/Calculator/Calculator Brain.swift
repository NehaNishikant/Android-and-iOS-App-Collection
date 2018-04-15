//
//  Calculator Brain.swift
//  Calculator
//
//  Created by Neha Nishikant on 9/28/17.
//  Copyright © 2017 Neha Nishikant. All rights reserved.
//

import Foundation //didn't import UI kit becasuse this is the model M in MVC. independent of UI

func changeSign(operand: Double) -> Double{ //"nice and global" lol
    return -operand
}

func multiply (op1: Double, op2: Double) -> Double{
    return op1*op2;
}

struct CalculatorBrain{ //similar to class but 1. structs do not have inheritence, 2. classes are in the heap so have pointers, structs are passed on by copying. but none of these things are necessary
    
    private var accumulator: Double? //private so no one else can access it (internal var), this var helps out the result before published on the display of calc, structs automatically initialize their vars. but we want our accumlator not to be set at first so we make it an optional
    
    private enum /*discrete vaules*/ Operation{ //optional is a type of enum with cases set and not set
        case constant(Double) //(associated values)
        case unaryOperation((Double)-> Double) //this means a function (which is a normal type in swift that takes in a double a returns a double
        case binaryOperation((Double, Double)-> Double)
        case equals
    }
    
    private var operations: Dictionary<String, Operation> = /*table to hold constants for operation*/[
        "π" : Operation.constant(Double.pi),
        "e" : Operation.constant(M_E),
        "√": Operation.unaryOperation(sqrt),
        "cos": Operation.unaryOperation(cos),
        "﬩": Operation.unaryOperation(changeSign),
        /*"*": Operation.binaryOperation({(op1: Double, op2: Double) -> Double in
            return op1*op2;
            }
        ),*/
        "*": Operation.binaryOperation({$0*$1}), //don't need the "Double" types or "return" because Swift knows that this is a function (Double, Double) -> Double because that is how we defined binaryOperation in the Operation enum. Also, in Swfit, $0, $1, $2, etc.. are parameters of functions. so binary function has $0 and $1 --> this is called a closure
        "/": Operation.binaryOperation({$0/$1}),
        "+": Operation.binaryOperation({$0+$1}),
        "-": Operation.binaryOperation({$0-$1}),
        "=": Operation.equals
    ]
    mutating func performOperation(symbol: String){
        /*switch symbol{
        case "π":
            accumulator=Double.pi
        case "√":
            if let operand=accumulator{ //is accumulator a double yet? or unset
                accumulator=(sqrt(operand))
            }
        default:
            break
        }*/
        if let operation=operations[symbol]/*this is how you look up something in a dictionary, but it returns an optional because it may noy be in the dictionary*/{
            switch operation{
                case .constant(let value):
                    accumulator=value
                case .unaryOperation(let function):
                    if accumulator != nil{
                        accumulator=function(accumulator!)
                    }
                case.binaryOperation(let function):
                    if accumulator != nil{
                        pendingBinaryOperation = PendingBinaryOperation(function: function, firstOperand: accumulator!) //sets var to a new PendingBinaryOption Object with the parameters of the current accumulator and the function determined in the operations Dictionary. thus the first operand and function are saved into the object for later use
                    }
                case.equals:
                    performPendingBinaryOperation() //performs operation
            }
        }
    }
    
    mutating private func performPendingBinaryOperation(){
        //pbo?.perform(with: accumulator!) //question mark means that it will unwrap pbo if it can be unwrapped, ignored otherwise. but we will do it the normal way:
        if (pendingBinaryOperation != nil && accumulator != nil){
            accumulator = pendingBinaryOperation!.perform(with: accumulator!) //calls the perform function in the with this pendingBinaryOperation object. parameteter: current accumulator=second operand --> first operand already saved into this object earlier
            pendingBinaryOperation = nil //now we are no longer in the middle of a pending binary operation
        }
    }
    
    private var pendingBinaryOperation: PendingBinaryOperation? //optional because we are not always in the middle of a binary operation
    
    private struct PendingBinaryOperation{ //inner struct
        let function: (Double, Double) -> Double
        let firstOperand: Double
        
        func perform(with secondOperand: Double) -> Double { //when someone calls this, the will call it as perform(with: 5.0)
            return function(firstOperand, secondOperand)
        }
    }
    
    mutating func setOperand(operand: Double){ //we neeed to tell struct when we are changing vars because struct passes by copying
        accumulator=operand //operand is the # u press
    
    }
    
    var result: Double?{ //optional because not always set. only set after the "=" is pressed
        get{ //without set, makes this read-only becsue we dont want others to change out result
            return accumulator
        }
    }

}
