import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AssetManagementSystem {

	//patterns to use regex to validate item id
	private static final Pattern idPatternLaptop = Pattern.compile("^L\\d{3}$");
	private static final Pattern idPatternMonitor = Pattern.compile("^M\\d{3}$");
	private static final Pattern idPatternPhone = Pattern.compile("^P\\d{3}$");
	private static final Pattern idPatternSoftware = Pattern.compile("^S\\d{3}$");
	private static final Pattern idPatternEmployee = Pattern.compile("^E\\d{3}$");
	private static final Pattern licenseKeyPattern = Pattern.compile("^[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}$");
	private static final Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

	public static void main(String[] args) {
		//Array List to store assets
		ArrayList<Asset> inventory = new ArrayList<>();
		Scanner scn = new Scanner(System.in);

		//Array Lists to store Employees
		ArrayList<Employee> employeeList = new ArrayList<>();

		//call loadInventory
		try {
			AssetLoader.loadInventory("inventory.txt", inventory);
			EmployeeLoader.loadEmployees("employees.txt", employeeList);

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();

		}
		catch(MatchingItemException mie) {
			System.out.println(mie.getMessage());
		}

		//display menu
		displayMenu();

		//get choice from user
		boolean runner = true;
		while(runner)
		{

			try {
				System.out.println("Enter your choice (1-11): ");
				int choice = scn.nextInt();

				//handle out of range
				if(choice < 1 || choice > 11)
				{
					System.out.println("Input must be 1-11!!");
					scn.nextLine();
					continue;
				}

				//switch case
				switch(choice) 
				{

				//display all items
				case 1:
					//call displayAllItems
					AssetUtility.displayAllItems(inventory);
					displayMenu();
					break;

					//search methods
				case 2:
					//keeps user in search menu until they choose to exit
					boolean searchMenu = true;

					//get choice from user
					while(searchMenu)
					{
						//display search sub menu
						System.out.println("----- SEARCH MENU -----");
						System.out.println("1. Search by Make");
						System.out.println("2. Search by Model");
						System.out.println("3. Search by Item ID");
						System.out.println("4. Back to Main Menu");
						System.out.println();

						System.out.println("Enter your choice (1-4): ");
						int subChoice = scn.nextInt();

						//handle out of range
						if(subChoice < 1 || subChoice > 4)
						{
							System.out.println("Input must be 1-4!!");
							scn.nextLine();
							continue;
						}

						//switch case
						switch(subChoice)
						{
						case 1:
							//clear scanner
							scn.nextLine();
							//get target from user
							System.out.println("Enter Make: ");
							//clean user input
							String make = scn.nextLine().trim(); 
							//call search method
							AssetUtility.searchByMake(inventory, make);
							break;

						case 2:
							//clear scanner
							scn.nextLine();
							//get target from user
							System.out.println("Enter Model: ");
							//clean user input
							String model = scn.nextLine().trim(); 
							//call search method
							AssetUtility.searchByModel(inventory, model);
							break;

						case 3:
							//clear scanner
							scn.nextLine();
							//get target from user
							System.out.println("Enter ID: ");
							//clean user input
							String id = scn.nextLine().trim(); 
							//call search method
							int index = AssetUtility.searchById(inventory, id);
							if(index != -1)
							{
								Asset item = inventory.get(index);
								System.out.println("Item Found Successfully!");
								System.out.println(item.getAssetDetails());
							}
							else {
								System.out.println("No such item found with ID " + id);
							}
							break;
						case 4:
							//prints main menu
							displayMenu();
							//ends search menu loop
							searchMenu = false;
							break;
						}
					}
					//exits case 2 and brings user back to main menu
					break;

					//sort methods
				case 3:
					//keeps user in search menu until they choose to exit
					boolean sortMenu = true;

					while(sortMenu)
					{
						//Display sort sub menu
						System.out.println("----- SORT MENU -----\n"
								+ "1. Sort by Make (A-Z)\n"
								+ "2. Sort by Model (A-Z)\n"
								+ "3. Sort by Item Type (Laptop/Monitor/Phone/Software)\n"
								+ "4. Back to Main Menu\n"
								+ "\n");

						System.out.println("Enter your choice (1-4): ");
						int subChoice = scn.nextInt();

						//handle out of range
						if(subChoice < 1 || subChoice > 4)
						{
							System.out.println("Input must be 1-4!!");
							scn.nextLine();
							continue;
						}

						//declare criteria
						String criteria = null;

						//switch case
						switch(subChoice)
						{
						case 1:
							criteria = "make";
							AssetUtility.sortItems(inventory, criteria);
							//displays sorted items
							AssetUtility.displayAllItems(inventory);
							break;
						case 2:
							criteria = "model";
							AssetUtility.sortItems(inventory, criteria);
							//displays sorted items
							AssetUtility.displayAllItems(inventory);
							break;
						case 3:
							criteria = "type";
							AssetUtility.sortItems(inventory, criteria);
							//displays sorted items
							AssetUtility.displayAllItems(inventory);
							break;
						case 4:
							//prints main menu
							System.out.println();
							System.out.println();
							System.out.println();
							displayMenu();
							//ends sort menu loop
							sortMenu = false;
							break;
						}
					}
					//exits case 3 and brings user back to main menu
					break;

					//check out item
				case 4:
					//clear scanner
					scn.nextLine();

					//prompt for item id
					//get target from user
					System.out.println("Enter Asset ID: ");

					//clean user input
					String idAsset = scn.nextLine().trim();

					//prompt user for employee id
					System.out.println("Enter Employee ID: ");
					String idEmployee = scn.nextLine().trim();

					//call searchEmployee to validate id
					if(EmployeeUtility.searchEmployee(employeeList, idEmployee)!=-1) {

						//call checkout item with target id
						AssetUtility.checkOutItem(inventory, employeeList, idAsset, idEmployee);
						displayMenu();
					}
					else {
						System.out.println("Employee with ID: " + idEmployee+ " does "
								+ "not exist!");
						continue;
					}
					break;

					//return item
				case 5:
					//clear scanner
					scn.nextLine();

					//prompt for item id
					//get target from user
					System.out.println("Enter Asset ID: ");
					idAsset = scn.nextLine().trim();

					//prompt user for employee id
					System.out.println("Enter Employee ID: ");
					idEmployee = scn.nextLine().trim();

					//call searchEmployee to validate id
					if(EmployeeUtility.searchEmployee(employeeList, idEmployee)!=-1) {

						//call returnItem with target id
						AssetUtility.returnItem(inventory, employeeList, idAsset, idEmployee);
						displayMenu();
					}
					else {
						System.out.println("Employee with ID: " + idEmployee+ " does "
								+ "not exist!");
						continue;
					}

					break;

					//addNewItem
				case 6:
					//clear scanner
					scn.nextLine();
					//loop that keeps user in add menu until correct input
					boolean addMenu = true;

					//ask user for item type
					while(addMenu) {
						System.out.println("1.Laptop\n2.Monitor\n3.Phone\n4.Software\n5.Return to Main Menu\nEnter Choice (1-5): ");
						int addChoice = scn.nextInt();

						//handle out of range
						if(addChoice < 1 || addChoice > 5)
						{
							System.out.println("Input must be 1-5!");
							scn.nextLine();
							continue;
						}

						//switch for item type
						switch(addChoice) {

						//Laptop
						case 1: 
							//clear scanner
							scn.nextLine();
							//prompt user for common fields
							System.out.println("Enter ASSET ID, BRAND, MODEL, STATUS, ASSIGNMENT, RAM, STORAGE, CPU: ");
							String line = scn.nextLine();

							//split line into an array
							String[] lineSeg = line.split(",");

							//check length of array
							if (lineSeg.length != 8) {
								System.out.println("Format is ASSET ID, BRAND, MODEL, STATUS, ASSIGNMENT, RAM, STORAGE, CPU!");
								continue;
							}

							//validate id using regex
							if (!idPatternLaptop.matcher(lineSeg[0].trim()).matches()) {
								System.out.println("Item ID for Laptop must follow format: L###");
								continue;
							}

							//validate numeric inputs
							try {
								int ram = Integer.parseInt(lineSeg[5].trim());
								if(ram < 0)
								{
									System.out.println("Ram cannot be negative!");
									continue;
								}
								else if(ram > 256) {
									System.out.println("Ram cannot greater than 256GB!");
									continue;
								}
							} catch(NumberFormatException nfe)
							{
								System.out.println("RAM must be a number!");
								continue;
							}
							try {
								int storage = Integer.parseInt(lineSeg[6].trim());
								if(storage < 0)
								{
									System.out.println("Storage cannot be negative!");
									continue;
								}
								else if(storage > 8000) 
								{
									System.out.println("Storage cannot be greater than 8TB!");
									continue;
								}
							} catch(NumberFormatException nfe)
							{
								System.out.println("Storage must be a number!");
								continue;
							}

							//try to add new item
							try {
								AssetUtility.addNewItem(inventory, lineSeg, addChoice);
							} catch (MatchingItemException mie) {
								System.out.println(mie.getMessage());
								continue;
							} catch (InvalidItemException iie) {
								System.out.println(iie.getMessage());
								continue;
							}
							break;

							//Monitor
						case 2:
							//clear scanner
							scn.nextLine();
							//prompt user for common fields
							System.out.println("Enter ASSET ID, MAKE, MODEL, STATUS, ASSIGNMENT, SIZE IN INCHES, RESOLUTION separated by commas: ");
							String line2 = scn.nextLine();

							//split line into an array
							String[] lineSeg2 = line2.split(",");

							//check length of array
							if (lineSeg2.length != 7) {
								System.out.println("Format is ASSET ID, MAKE, MODEL, STATUS, ASSIGNMENT, SIZE IN INCHES, RESOLUTION!");
								continue;
							}

							//validate id using regex
							if (!idPatternMonitor.matcher(lineSeg2[0].trim()).matches()) {
								System.out.println("Item ID for Monitor must follow format: M###");
								continue;
							}


							//validate numeric inputs
							try {
								double size = Double.parseDouble(lineSeg2[5].trim());
								if(size < 0)
								{
									System.out.println("Size cannot be negative!");
									continue;
								}
							} catch(NumberFormatException nfe)
							{
								System.out.println("Size must be a number!");
								continue;
							}

							//try to call addNewItem
							try {
								AssetUtility.addNewItem(inventory, lineSeg2, addChoice);
							} catch (MatchingItemException mie) {
								System.out.println(mie.getMessage());
								continue;
							} catch (InvalidItemException iie) {
								System.out.println(iie.getMessage());
								continue;
							} 
							break;

							//Phone
						case 3:
							//clear scanner
							scn.nextLine();
							//prompt user for common fields
							System.out.println("Enter ASSET ID, MAKE , MODEL, STATUS, ASSIGNMENT, CARRIER, OS, STORAGE separated by commas: ");
							String line3 = scn.nextLine();

							//split line into an array
							String[] lineSeg3 = line3.split(",");

							//check length of array
							if (lineSeg3.length != 8) {
								System.out.println("Format is ASSET ID, MAKE , MODEL, STATUS, ASSIGNMENT, CARRIER, OS, STORAGE!");
								continue;
							}

							//validate id using regex
							if (!idPatternPhone.matcher(lineSeg3[0].trim()).matches()) {
								System.out.println("Item ID for Phone must follow format: P###");
								continue;
							}

							//validate storage
							try {
								int storage = Integer.parseInt(lineSeg3[7].trim());
								if (storage < 0 || storage > 8000) {
									System.out.println("Storage must be between 0 and 8000GB!");
									continue;
								}
							} catch (NumberFormatException nfe) {
								System.out.println("Storage must be a number!");
								continue;
							}


							//try to call addNewItem
							try {
								AssetUtility.addNewItem(inventory, lineSeg3, addChoice);
							} catch (MatchingItemException mie) {
								System.out.println(mie.getMessage());
								continue;
							} catch (InvalidItemException iie) {
								System.out.println(iie.getMessage());
								continue;
							}
							break;

							//Software
						case 4:
							//clear scanner
							scn.nextLine();
							//prompt user for common fields
							System.out.println("Enter ASSET ID, MAKE , MODEL, STATUS, ASSIGNMENT, LICENSE KEY, EXPIRY DATE separated by commas: ");
							String line4 = scn.nextLine();

							//split line into an array
							String[] lineSeg4 = line4.split(",");

							//check length of array
							if (lineSeg4.length != 7) {
								System.out.println("Format is ASSET ID, MAKE , MODEL, STATUS, ASSIGNMENT, LICENSE KEY, EXPIRY DATE!");
								continue;
							}

							//validate using regex
							if (!idPatternSoftware.matcher(lineSeg4[0].trim()).matches()) {
								System.out.println("Item ID for Software must follow format: S###");
								continue;
							}
							if (!licenseKeyPattern.matcher(lineSeg4[5].trim()).matches()) {
								System.out.println("License Key must follow format: XXXXX-XXXXX-XXXXX");
								continue;
							}

							if (!datePattern.matcher(lineSeg4[6].trim()).matches()) {
								System.out.println("Expiration Date must follow format: YYYY-MM-DD");
								continue;
							}


							//try to call addNewItem
							try {
								AssetUtility.addNewItem(inventory, lineSeg4, addChoice);
							} catch (MatchingItemException mie) {
								System.out.println(mie.getMessage());
								continue;
							} catch (InvalidItemException iie) {
								System.out.println(iie.getMessage());
								continue;
							}
							break;

						case 5:
							//prints main menu
							displayMenu();
							//ends add menu loop
							addMenu = false;
							break;
						}
					}
					break;

					//removes item 
				case 7:
					//clear scanner
					scn.nextLine();
					//prompt for item id
					//get target from user
					System.out.println("Enter ID: ");
					//clean user input
					String id = scn.nextLine().trim(); 

					//create an int index to be passed into removeItem
					int index = AssetUtility.searchById(inventory, id);

					//call search by id and validate that it can be found
					if(index!=-1)
					{
						Asset item = inventory.get(index);
						while(true) 
						{
							System.out.println("Are you sure you would like to remove item: " + item.getModel()
							+ "? Enter Y for yes or N for no: ");
							String finalChoice = scn.nextLine();

							//validate input
							if(!finalChoice.toLowerCase().equals("y") && !finalChoice.toLowerCase().equals("n"))
							{
								System.out.println("Input must be Y or N!");
								continue;
							}

							//if user picks yes
							else if(finalChoice.toLowerCase().equals("y"))
							{
								AssetUtility.removeItem(inventory, index);
								break;
							}

							//user picks no
							else if(finalChoice.toLowerCase().equals("n"))
							{
								System.out.println("Removal of item: " +item.getModel() + " cancelled!");
								break;
							}
						}
					}
					else
					{
						System.out.println("Item: " +id + " could not be found!");
					}
					//prints main menu
					displayMenu();
					break;

					//displays available items
				case 8:
					AssetUtility.displayAvailableItems(inventory);
					displayMenu();
					break;

					//displays checked out items
				case 9:
					AssetUtility.displayCheckedOutItems(inventory);
					displayMenu();
					break;

				case 10:
					//clear scanner buffer first
					scn.nextLine();

					//keeps user in employee menu until they choose to exit
					boolean empMenu = true;

					while(empMenu) {
						//Display employee sub menu
						System.out.println("----- MANAGE EMPLOYEES -----\n"
								+ "1. Search Employee\n"
								+ "2. Add Employee\n"
								+ "3. Remove Employee\n"
								+ "4. Return to main menu\n"
								+ "\n");

						//user input
						System.out.println("Enter your choice (1-4): ");
						int empChoice = scn.nextInt();
						scn.nextLine(); // clear buffer after nextInt()

						//handle out of range
						if(empChoice < 1 || empChoice > 4)
						{
							System.out.println("Input must be 1-4!!");
							continue;
						}

						//switch case for selection
						switch(empChoice) {

						//search employee
						case 1:
							//prompt for employee id
							System.out.println("Enter ID (E###) of employee you wish to search: ");
							String empId = scn.nextLine().trim();

							int empIndex = EmployeeUtility.searchEmployee(employeeList, empId);

							if(empIndex != -1)
							{
								Employee emp = employeeList.get(empIndex);
								System.out.println("Employee Found Successfully!");
								System.out.println(emp.getEmployeeDetails());
							}
							else
							{
								System.out.println("No employee with ID: " + empId + " could be found.");
							}
							break;

							//add employee
						case 2:
							System.out.println("Enter ID number and employee name (first and last) separated by commas: ");
							String empInput = scn.nextLine();

							String[] empArr = empInput.split(",");

							//validate length
							if (empArr.length != 2) {
								System.out.println("Invalid input format. Please enter ID and name separated by a comma.");
								continue;
							}

							//validate id using regex
							if (!idPatternEmployee.matcher(empArr[0].trim()).matches()) {
								System.out.println("Employee ID must follow format: E###");
								continue;
							}

							try {
								EmployeeUtility.addNewEmployee(employeeList, empArr);
							} catch (MatchingItemException mie) {
								System.out.println(mie.getMessage());
							} catch (InvalidItemException iie) {
								System.out.println(iie.getMessage());
							}
							break;

							//remove employee
						case 3:
							System.out.println("Enter ID (E###) of employee you wish to remove: ");
							String removeId = scn.nextLine().trim();

							int removeIndex = EmployeeUtility.searchEmployee(employeeList, removeId);

							if(removeIndex != -1) {
								Employee empToRemove = employeeList.get(removeIndex);
								System.out.println("Are you sure you want to remove " + empToRemove.getEmployeeName() + "? (Y/N): ");
								String confirm = scn.nextLine().trim();

								if(confirm.equalsIgnoreCase("y")) {
									employeeList.remove(removeIndex);
									System.out.println("Employee removed successfully!");

									int released = EmployeeUtility.unassignAssetsForDeletedEmployee(inventory, removeId);
									System.out.println("Unassigned " + released + " asset(s) from deleted employee " + removeId + ".");
								} else {
									System.out.println("Removal cancelled.");
								}
							}
							break;

							//return to main menu
						case 4:
							displayMenu();
							empMenu = false;
							break;
						}
					}
					break;

				case 11:
					//ends program
					System.out.println("Goodbye!");
					runner = false;
					//close scanner
					scn.close();
				}

			} catch (InputMismatchException ime) {
				System.out.println("Input must be an Int!");
				scn.nextLine();
				continue;
			}
		}
	}
	
	//displays main menu
	private static void displayMenu() {
		System.out.println();
		System.out.println("----- MAIN MENU -----");
		System.out.println("1.  Display All Items");
		System.out.println("2.  Search Items");
		System.out.println("3.  Sort Items");
		System.out.println("4.  Check Out Item");
		System.out.println("5.  Return Item");
		System.out.println("6.  Add New Item");
		System.out.println("7.  Remove Item");
		System.out.println("8.  Display Available Items Only");
		System.out.println("9.  Display Checked Out Items");
		System.out.println("10. Employee Management");
		System.out.println("11. Exit");
		System.out.println();

	}

}
