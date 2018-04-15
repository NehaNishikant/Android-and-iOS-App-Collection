//
//  Category.swift
//  GroceriesDraft2
//
//  Created by Neha Nish on 1/20/18.
//  Copyright © 2018 Neha Nish. All rights reserved.
//

import Foundation
import os.log

class Category: NSObject, Comparable, NSCoding{
    
    func encode(with aCoder: NSCoder) {
        aCoder.encode(name, forKey: PropertyKey.name)
        aCoder.encode(list, forKey: PropertyKey.list)
    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        guard let name = aDecoder.decodeObject(forKey: PropertyKey.name) as? String else {
            os_log("Unable to decode the name for a Meal object.", log: OSLog.default, type: .debug)
            return nil
        }
        let list = aDecoder.decodeObject(forKey: PropertyKey.list) as? Array<String>
        self.init(name: name, list: list!)
    }
    
    //MARK: Archiving Paths
    
    static let DocumentsDirectory = FileManager().urls(for: .documentDirectory, in: .userDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.appendingPathComponent("categories")
    
    var name=""
    var list = [String]()
    
    init(name: String){
        self.name=name
    }
    
    init(name: String, list: Array<String>){
        self.name=name
        self.list=list
    }
    
    func setList(list: [String]){
        self.list=list
    }
    
    func add(item: String){
        var index=list.count-1;
        for i in list{
            if (i<item){
                index=list.index(of: i)!
            }
        }
        list.insert(item, at: index+1)
    }
    func remove(item: String){
        if let i = list.index(of: item){
            list.remove(at: i)
        }
    }
    static func < (lhs: Category, rhs: Category) -> Bool {
        return lhs.name < rhs.name
    }
    
    static func == (lhs: Category, rhs: Category) -> Bool {
        return lhs.name == rhs.name
    }
}

struct PropertyKey {
    
    static let name="name"
    static let list="list"
}
