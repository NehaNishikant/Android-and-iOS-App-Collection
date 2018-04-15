//
//  ViewController.swift
//  GroceriesDraft2
//
//  Created by Neha Nish on 1/20/18.
//  Copyright © 2018 Neha Nish. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    var newCategoryText=""

    @IBAction func updateNewCategory(_ sender: UITextField) {
        newCategoryText=sender.text!
    }
    
    @IBAction func addNewCategory(_ sender: UIButton) {
        //add to tableview
        //add to brain
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

