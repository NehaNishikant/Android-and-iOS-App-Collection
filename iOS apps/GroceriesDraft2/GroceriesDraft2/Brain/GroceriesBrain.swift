//
//  GroceriesBrain.swift
//  GroceriesDraft2
//
//  Created by Neha Nish on 1/20/18.
//  Copyright © 2018 Neha Nish. All rights reserved.
//

import Foundation

struct GroceriesBrain{
    var masterList = [Category]()
    
    init(){
        let c=Category(name: "list")
        masterList.append(c);
    }
    
    func getMainList() -> Array<Category>{
        return masterList
    }
    
    mutating func add(name: Category){
        var index=masterList.count-1;
        for c in masterList{
            if (c<name){
                index=masterList.index(of: c)!
            }
        }
        masterList.insert(name, at: index+1)
    }
    mutating func remove(name: Category){
        if let i=masterList.index(of: name){
            masterList.remove(at: i)
        }
    }
    mutating func addItem(category: Category, item: String){
        if let i=masterList.index(of: category){
            let c=masterList[i]
            c.add(item: item);
            masterList[i]=c;
        }
    }
    mutating func removeItem(category: Category, item: String){
        if let i=masterList.index(of: category){
            let c=masterList[i]
            c.remove(item: item);
            masterList[i]=c;
        }
    }
    
}
