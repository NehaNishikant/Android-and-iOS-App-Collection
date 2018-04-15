//
//  ItemTableViewCell.swift
//  GroceriesDraft2
//
//  Created by Neha Nish on 1/20/18.
//  Copyright © 2018 Neha Nish. All rights reserved.
//

import UIKit

class ItemTableViewCell: UITableViewCell {

    @IBOutlet weak var itemName: UILabel!
    
    func setTitle(name: String){
        itemName.text=name
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
