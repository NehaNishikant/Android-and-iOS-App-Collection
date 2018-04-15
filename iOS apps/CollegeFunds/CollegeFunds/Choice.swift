//
//  Choice.swift
//  CollegeFunds
//
//  Created by Neha Nish on 11/9/17.
//  Copyright © 2017 Neha Nish. All rights reserved.
//

import Foundation

class Choice{
    
    var label: String
    var show: Bool
    var operation: ((Double) -> Double)?
    var description: String
    
    init(label: String, show: Bool, description: String){
        self.label=label
        self.show=show
        self.description=description
    }
    
    func setOperation(operation: @escaping (Double)->Double){
        self.operation=operation
    }
    
}
