import java.util.ArrayList;

public class EmployeeUtility {

	/**
	 * Unassigns all assets from a deleted employee and returns them to IN_STOCK status.
	 * This method should be called when removing an employee to prevent orphaned assignments.
	 * 
	 * @param inv The list of all assets
	 * @param target The employee ID whose assets should be unassigned
	 * @return The number of assets that were unassigned
	 */
	static int unassignAssetsForDeletedEmployee(ArrayList<Asset> inv, String target) {
		//count assets that are assigned to employee
		int count = 0;

		//loop through inventory
		for (int i = 0; i < inv.size(); i++) {
			Asset asset = inv.get(i);

			//check if asset is assigned and if it equals target
			if (asset.getStatus() == AssetStatus.ASSIGNED
					&& asset.getAssignedTo() != null
					&& asset.getAssignedTo().trim().equalsIgnoreCase(target.trim())) 
			{
				//clear assignment
				asset.setAssignedTo(""); 
				count++;
			}
		}
		return count;
	}

	//method to add new employee
	static void addNewEmployee(ArrayList<Employee> list, String[] input) throws MatchingItemException, InvalidItemException {
		//prevent invalid array lengths
		if(input.length != 2)
		{
			throw new InvalidItemException("Employee must follow format: ID, NAME!");
		}


		//declare new Employee
		Employee newEmployee;

		//iterate through input to clean it
		for(int i=0; i < input.length; i++)
		{
			input[i] = input[i].trim();
		}

		//create new item 
		newEmployee = new Employee(
				input[0], input[1]);

		//prevent duplacate values by looping through library
		for(int i = 0; i < list.size(); i++)
		{
			//get item from library
			Employee emp = list.get(i);

			//check to see if values match existing ones
			if(input[0].trim().equals(emp.getEmployeeID().trim()))
			{
				throw new MatchingItemException("Employee with this ID# already exists: " +input[0]);
			}
		}


		//add media item to library
		list.add(newEmployee);
		System.out.println("Successfully added\n" +newEmployee.getEmployeeDetails());

	}

	//method to search employees
	static int searchEmployee(ArrayList<Employee> list, String target) {
		//loop through library until target is found
		for(int i = 0; i < list.size(); i++)
		{
			//get item from library
			Employee e = list.get(i);

			//if target is found
			if(e.getEmployeeID().trim().toUpperCase().equals(target.trim().toUpperCase())) {
				return i;
			}
		}
		//if target is not found
		System.out.println("No Such Employee Found With ID# " + target);
		return -1;
	}
}
