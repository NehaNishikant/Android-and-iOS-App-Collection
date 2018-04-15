//
//  CategoryTableViewController.swift
//  GroceriesDraft2
//
//  Created by Neha Nish on 1/20/18.
//  Copyright © 2018 Neha Nish. All rights reserved.
//

import UIKit

class CategoryTableViewController: UITableViewController {

    var newItemText: String="";
    var categoryNameString: String=""
    var items=[String]()
    @IBOutlet weak var newItem: UITextField!
    @IBOutlet weak var categoryName: UILabel!
    
    @IBAction func addNewItem(_ sender: UIButton) {
        items.append(newItemText)
        //add to brain
        let newIndexPath = IndexPath(row: items.count-1, section: 0)
        tableView.insertRows(at: [newIndexPath], with: .automatic)
    }
    
    @IBAction func updateNewItem(_ sender: UITextField) {
        newItemText=sender.text!
    }

    func setCategoryName(name: String){
        categoryNameString=name
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        categoryName.text!=categoryNameString+": "
        var counter=0;
        tableView.reloadData()
        let cells = self.tableView.visibleCells as! Array<ItemTableViewCell>
        print("there are already "+String(cells.count)+" cells in categoryVC")
        for cell in cells {
            // look at data
            cell.setTitle(name: items[counter])
            counter=counter+1
        }
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false
        
        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return items.count
    }
    
    
    /*
    // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
    */

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "itemCellIdentifier", for: indexPath) as! ItemTableViewCell
        cell.setTitle(name: newItemText)
        newItemText=""
        newItem.text!=""
        // Configure the cell...

        return cell
    }
 

    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
 

    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            items.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
 

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */



}
