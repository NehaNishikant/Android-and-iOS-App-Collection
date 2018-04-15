//
//  MenuTableViewController.swift
//  GroceriesDraft2
//
//  Created by Neha Nish on 1/20/18.
//  Copyright © 2018 Neha Nish. All rights reserved.
//

import UIKit
import os.log

class MenuTableViewController: UITableViewController {
    
    var newCategoryText: String=""
    var categories = [Category]()
    var currentIndex: Int = -1
    @IBOutlet weak var newCategoryName: UITextField!

    @IBAction func updateNewCategory(_ sender: UITextField) {
        newCategoryText=sender.text!
    }
    
    @IBAction func addNewCategory(_ sender: UIButton) {
        newCategoryName.text!=""
        //add to brain
        //add to tableview
    }
    
    @IBAction func unwindToMenu(sender: UIStoryboardSegue) {
        print("unwind!")
        if let sourceViewController = sender.source as? CategoryTableViewController{
            if let selectedIndexPath = tableView.indexPathForSelectedRow { //editing old
                categories[selectedIndexPath.row].setList(list: sourceViewController.items)
                tableView.reloadRows(at: [selectedIndexPath], with: .none)
            } else { //adding new
                categories[categories.count-1].setList(list: sourceViewController.items)
                tableView.reloadData()
            }
            print("menu reloaded")
            var counter=0;
            let cells = self.tableView.visibleCells as! Array<CategoryTableViewCell>
            print("there are already "+String(cells.count)+" cells in menu")
            for cell in cells {
                // look at data
                cell.setTitle(name: categories[counter].name)
                counter=counter+1
            }
        }
        saveCategories()
    }
    
    private func saveCategories() {
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(categories, toFile: Category.ArchiveURL.path)
        if isSuccessfulSave {
            os_log("Categories successfully saved.", log: OSLog.default, type: .debug)
        } else {
            os_log("Failed to save Categories...", log: OSLog.default, type: .error)
        }
    }
    
    private func loadCategories() -> [Category]?  {
        return NSKeyedUnarchiver.unarchiveObject(withFile: Category.ArchiveURL.path) as? [Category]
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let savedCategories = loadCategories() {
            categories += savedCategories
        }
        tableView.reloadData()
        print("menu loaded")
        var counter=0;
        let cells = self.tableView.visibleCells as! Array<CategoryTableViewCell>
        print("there are already "+String(cells.count)+" cells in menu")
        for cell in cells {
            // look at data
            cell.setTitle(name: categories[counter].name)
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
        return categories.count
    }

    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        // Create a new variable to store the instance of PlayerTableViewController
        if let destinationVC = segue.destination as? CategoryTableViewController{
            destinationVC.setCategoryName(name: newCategoryText)
            switch(segue.identifier ?? "") {
                case "addItem":
                    categories.append(Category(name: newCategoryText))
                    let newIndexPath = IndexPath(row: categories.count-1, section: 0)
                    tableView.insertRows(at: [newIndexPath], with: .automatic)
                case "showDetail":
                    if let categoryCell=sender as? CategoryTableViewCell{
                        destinationVC.categoryNameString=categoryCell.categoryName.text!
                        destinationVC.setCategoryName(name: categoryCell.categoryName.text!)
                        if let indexPath = tableView.indexPath(for: categoryCell) {
                            let selectedCategory = categories[indexPath.row]
                            destinationVC.items=selectedCategory.list
                        }
                    }
                default:
                    print("segue error!")
            }
        }
        /*if let destinationVC = segue.destination as? CategoryTableViewController{
            destinationVC.setCategoryName(name: newCategoryText)
            if (justCreated){
                categories.append(Category(name: newCategoryText))
                let newIndexPath = IndexPath(row: categories.count-1, section: 0)
                tableView.insertRows(at: [newIndexPath], with: .automatic)
                justCreated=false
            } else {
                if let button=sender as? UIButton{
                    destinationVC.categoryNameString=button.title(for: .normal)!
                    destinationVC.setCategoryName(name: button.title(for: .normal)!)
                    //access item list from brain
                }
            }
        }*/
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "categoryCellIdentifier", for: indexPath) as! CategoryTableViewCell
        cell.setTitle(name: newCategoryText);
        cell.index=categories.count-1
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
            categories.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }
        saveCategories()
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
