import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AssetUtility {
	
	//method to display checked out items
	static void displayCheckedOutItems(ArrayList<Asset> inventory) {
		//count to keep track of unavailable items
		int count = 0;

		//loop through library and display items if they are unavailable
		System.out.println("Checked Out Items: ");
		for(int i = 0; i < inventory.size(); i++)
		{
			//get item from library
			Asset asset = inventory.get(i);

			if((asset.getStatus() == AssetStatus.ASSIGNED))
			{
				System.out.println(asset.getAssetDetails());

			}
			else {
				count++;
			}
		}
		//if all items are available
		if(count==inventory.size())
		{
			System.out.println("All Items Are Available!");
		}

	}

	/**
	 * Returns an asset from an employee, changing its status back to IN_STOCK.
	 * Validates that the asset exists, is currently assigned, and belongs to the specified employee.
	 * 
	 * @param inventory The list of all assets
	 * @param employeeList The list of all employees
	 * @param assetId The unique ID of the asset being returned
	 * @param idEmployee The unique ID of the employee returning the asset
	 */
	static void returnItem(ArrayList<Asset> inventory, ArrayList<Employee> employeeList,
			String assetId, String idEmployee) {

		int searched = searchById(inventory, assetId);

		if (searched == -1) {
			System.out.println("No such asset found with ID " + assetId);
			return;
		}

		Asset asset = inventory.get(searched);

		if (asset.getStatus() != AssetStatus.ASSIGNED) {
			System.out.println("Asset is not currently assigned, so it cannot be returned.");
			return;
		}

		String employeeName = EmployeeLoader.idToName(employeeList, idEmployee);

		if (!asset.getAssignedTo().trim().equalsIgnoreCase(idEmployee.trim())) {
			System.out.println("That asset is not assigned to employee " + employeeName);
			return;
		}

		// clear assignment (status auto-normalizes to IN_STOCK)
		asset.setAssignedTo("");

		System.out.println("Asset: " + asset.getModel() +
				" has been unassigned from employee: " + employeeName + "!");
	}

	/**
	 * Assigns an asset to an employee, changing its status to ASSIGNED.
	 * Validates that the asset exists and is available (IN_STOCK status).
	 * 
	 * @param inventory The list of all assets
	 * @param list The list of all employees
	 * @param assetId The unique ID of the asset to check out
	 * @param employeeId The unique ID of the employee receiving the asset
	 */
	static void checkOutItem(ArrayList<Asset> inventory, ArrayList<Employee> list,
			String assetId, String employeeId) {

		int searched = searchById(inventory, assetId);

		if (searched == -1) {
			System.out.println("No such asset found with ID " + assetId);
			return;
		}

		Asset asset = inventory.get(searched);

		if (asset.getStatus() != AssetStatus.IN_STOCK) {
			System.out.println("Asset is not available.");
			return;
		}

		// assignment sets status to ASSIGNED automatically
		asset.setAssignedTo(employeeId);

		String employeeName = EmployeeLoader.idToName(list, employeeId);

		System.out.println("Asset: " + asset.getModel() +
				" has been assigned to employee: " + employeeName + "!");
	}


	//method to remove item from inventory
	static void removeItem(ArrayList<Asset> inventory, int i) {
		System.out.println("Item Successfully Removed!");
		//removes item i from the library
		inventory.remove(i);

	}

	//method to display available assets
	static void displayAvailableItems(ArrayList<Asset> inventory) {
		//count to keep track of unavailable items
		int count = 0;

		//loop through library and display items if they are available
		System.out.println("Available Items: ");
		for(int i = 0; i < inventory.size(); i++)
		{
			//get item from library
			Asset asset = inventory.get(i);

			if((asset.getStatus() == AssetStatus.IN_STOCK))
			{
				System.out.println(asset.getAssetDetails());
			}
			else {
				count++;
			}
		}
		//if all items are available
		if(count==inventory.size())
		{
			System.out.println("Sorry, All Items Are Unavailable");
		}

	}

	/**
	 * Adds a new asset to the inventory based on user input.
	 * Validates the input format, prevents duplicate IDs, and creates the appropriate
	 * asset type (Laptop, Monitor, Phone, or Software) based on the selection.
	 * 
	 * @param inventory The list of all assets
	 * @param input Array of strings containing asset details from user input
	 * @param selection Integer representing asset type (1=Laptop, 2=Monitor, 3=Phone, 4=Software)
	 * @throws MatchingItemException If an asset with the same ID already exists
	 * @throws InvalidItemException If the input format is incorrect or validation fails
	 */
	static void addNewItem(ArrayList<Asset> inventory, String[] input, int selection) throws MatchingItemException, InvalidItemException{
		//add new laptop
		if(selection == 1)
		{
			//prevent invalid array lengths
			if(input.length != 8)
			{
				throw new InvalidItemException("Item must follow format: ASSET ID, MAKE, MODEL, STATUS, ASSIGNMENT, RAM, STORAGE, CPU!");
			}

			//declare new Asset
			Asset newAsset;

			//iterate through input to clean it
			for(int i=0; i < input.length; i++)
			{
				input[i] = input[i].trim();
			}

			//gets string value of AssetStatus
			AssetStatus status = AssetStatus.valueOf(input[3].trim().toUpperCase());
			String assignedTo = input[4].trim();

			//create new item 
			newAsset = new Laptop(
					input[0], input[1], input[2],
					status, assignedTo,
					Integer.parseInt(input[5]), Integer.parseInt(input[6]), input[7]
					);

			//prevent duplacate values by looping through library
			for(int i = 0; i < inventory.size(); i++)
			{
				//get item from library
				Asset item = inventory.get(i);

				//check to see if values match existing ones
				if(input[0].trim().equals(item.getAssetId().trim()))
				{
					throw new MatchingItemException("Item with this parameter already exists: " +input[0]);
				}
			}


			//add media item to library
			inventory.add(newAsset);
			System.out.println("Successfully added\n" +newAsset.getAssetDetails());
		}

		//add new monitor
		if(selection == 2)
		{
			//prevent invalid array lengths
			if(input.length != 7)
			{
				throw new InvalidItemException("Item must follow format: ASSET ID, MAKE, MODEL, STATUS, ASSIGNMENT, SIZE IN INCHES, RESOLUTION!");
			}

			//declare new Asset
			Asset newAsset;

			//iterate through input to clean it
			for(int i=0; i < input.length; i++)
			{
				input[i] = input[i].trim();
			}

			AssetStatus status = AssetStatus.valueOf(input[3].trim().toUpperCase());
			String assignedTo = input[4].trim();

			//create new item 
			newAsset = new Monitor(
					input[0], input[1], input[2],
					status, assignedTo,
					Double.parseDouble(input[5]), input[6]
					);

			//prevent duplacate values by looping through library
			for(int i = 0; i < inventory.size(); i++)
			{
				//get item from library
				Asset item = inventory.get(i);

				//check to see if values match existing ones
				if(input[0].trim().equals(item.getAssetId().trim()))
				{
					throw new MatchingItemException("Item with this parameter already exists: " +input[0]);
				}
			}

			//add media item to library
			inventory.add(newAsset);
			System.out.println("Successfully added\n" +newAsset.getAssetDetails());
		}

		//add new software
		if(selection == 4)
		{
			//prevent invalid array lengths
			if(input.length != 7)
			{
				throw new InvalidItemException("Item must follow format: ASSET ID, MAKE, MODEL, STATUS, ASSIGNMENT, LICENSE KEY, EXPIRY DATE!");
			}

			//declare new Asset
			Asset newAsset;

			//iterate through input to clean it
			for(int i=0; i < input.length; i++)
			{
				input[i] = input[i].trim();
			}

			//gets string value of AssetStatus
			AssetStatus status = AssetStatus.valueOf(input[3].trim().toUpperCase());
			String assignedTo = input[4].trim();

			//prevents invalid date
			LocalDate expiryDate;
			try {
				expiryDate = LocalDate.parse(input[6].trim());
			} catch (Exception e) {
				throw new InvalidItemException("Expiry Date must be in format YYYY-MM-DD");
			}

			//create new item 
			newAsset = new Software(
					input[0].trim(), input[1].trim(),
					input[2].trim(), status, assignedTo, 
					input[5].trim(), LocalDate.parse(input[6].trim()) // expiryDate
					);

			//prevent duplacate values by looping through library
			for(int i = 0; i < inventory.size(); i++)
			{
				//get item from library
				Asset item = inventory.get(i);

				//check to see if values match existing ones
				if(input[0].trim().equals(item.getAssetId().trim()))
				{
					throw new MatchingItemException("Item with this parameter already exists: " +input[0]);
				}
			}

			//add asset to inventory
			inventory.add(newAsset);
			System.out.println("Successfully added\n" +newAsset.getAssetDetails());
		}

		//add new phone
		if(selection == 3)
		{
			//prevent invalid array lengths
			if(input.length != 8)
			{
				throw new InvalidItemException("Item must follow format: ASSET ID, MAKE, MODEL, STATUS, ASSIGNMENT, CARRIER, OS, STORAGE!");
			}

			//declare new Asset
			Asset newAsset;

			//iterate through input to clean it
			for(int i=0; i < input.length; i++)
			{
				input[i] = input[i].trim();
			}

			//gets string value of AssetStatus
			AssetStatus status = AssetStatus.valueOf(input[3].trim().toUpperCase());
			String assignedTo = input[4].trim();

			//create new item 
			newAsset = new Phone(
					input[0], input[1], input[2],
					status, assignedTo,
					input[5],input[6], Integer.parseInt(input[7])
					);

			//prevent duplacate values by looping through library
			for(int i = 0; i < inventory.size(); i++)
			{
				//get item from library
				Asset item = inventory.get(i);

				//check to see if values match existing ones
				if(input[0].trim().equals(item.getAssetId().trim()))
				{
					throw new MatchingItemException("Item with this parameter already exists: " +input[0]);
				}
			}

			//add asset to inventory
			inventory.add(newAsset);
			System.out.println("Successfully added\n" +newAsset.getAssetDetails());
		}

	}

	/**
	 * Sorts the inventory list based on the specified criteria.
	 * Sorting is performed in ascending alphabetical order.
	 * 
	 * @param inventory The list of assets to sort
	 * @param criteria Sort criteria: "make" (brand), "model", or "type" (asset type by ID prefix)
	 */
	static void sortItems(ArrayList<Asset> inventory, String criteria) {
		//sort by title
		if(criteria.equals("model"))
		{
			//uses comparator to set upper and lower bounds to sort
			Collections.sort(inventory, new Comparator<Asset>(){
				@Override
				public int compare(Asset a1, Asset a2) {
					return a1.getModel().compareTo(a2.getModel());
				}
			});
			System.out.println("sorted by Model!");
		}

		//sorted by year ascending
		if(criteria.equals("make"))
		{
			//uses comparator to set upper and lower bounds to sort
			Collections.sort(inventory, new Comparator<Asset>(){
				@Override
				public int compare(Asset a1, Asset a2) {
					return a1.getMake().compareTo(a2.getMake());
				}
			});
			System.out.println("sorted by Make!");
		}

		//sorted by type
		if(criteria.equals("type"))
		{
			//uses comparator to set upper and lower bounds to sort
			Collections.sort(inventory, new Comparator<Asset>(){
				@Override
				public int compare(Asset a1, Asset a2) {
					return a1.getAssetId().compareTo(a2.getAssetId());
				}
			});
			System.out.println("sorted by type!");
		}

	}

	/**
	 * Searches for an asset in the inventory by its unique ID.
	 * The search is case-insensitive and trims whitespace.
	 * 
	 * @param inventory The list of assets to search through
	 * @param target The asset ID to search for (e.g., "L001", "M002", "P003")
	 * @return The index position of the asset in the list, or -1 if not found
	 */
	static int searchById(ArrayList<Asset> inventory, String target) {
		//loop through library
		for(int i = 0; i < inventory.size(); i++)
		{
			//get item from library
			Asset item = inventory.get(i);

			//if target is found
			if(item.getAssetId().trim().toLowerCase().equals(target.toLowerCase())) //converts to lower case in order to validate
			{
				return i;
			}
		}
		//if target is not found
		return -1;

	}

	//search by model
	static boolean searchByModel(ArrayList<Asset> inventory, String target) {
		//loop through library
		for(int i = 0; i < inventory.size(); i++)
		{
			//get item from library
			Asset item = inventory.get(i);

			//if target is found
			if(item.getModel().trim().toLowerCase().equals(target.toLowerCase())) //converts to lower case in order to validate
			{
				System.out.println("Item Found Successfully!");
				System.out.println(item.getAssetDetails());
				return true;
			}
		}
		//if target is not found
		System.out.println("No Such Item Found With Model " + target);
		return false;

	}

	//search by make
	static boolean searchByMake(ArrayList<Asset> inventory, String target) {
		//loop through library
		for(int i = 0; i < inventory.size(); i++)
		{
			//get item from library
			Asset item = inventory.get(i);

			//if target is found
			if(item.getMake().trim().toLowerCase().equals(target.toLowerCase())) //converts to lower case in order to validate
			{
				System.out.println("Item Found Successfully!");
				System.out.println(item.getAssetDetails());
				return true;
			}
		}
		//if target is not found
		System.out.println("No Such Item Found With Make " + target);
		return false;

	}

	//displays all items
	static void displayAllItems(ArrayList<Asset> inventory) {
		//loop through libray array list and print each items details
		for(int i = 0; i < inventory.size(); i++)
		{
			//get item from library
			Asset item = inventory.get(i);
			System.out.println(item.getAssetDetails());
		}

	}
}
