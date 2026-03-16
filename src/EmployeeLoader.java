import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class EmployeeLoader {

	//pattern for regex
	private static final Pattern idPatternEmployee = Pattern.compile("^E\\d{3}$");

	/**
	 * Loads employee data from a text file and populates the employee list.
	 * Automatically validates employee ID format (E###) and skips invalid entries,
	 * empty lines, and comments (lines starting with #).
	 *
	 * @param file Path to the employee text file (e.g., "employees.txt")
	 * @param list ArrayList to populate with Employee objects
	 * @throws FileNotFoundException If the specified file cannot be found
	 * @throws MatchingItemException If duplicate employee IDs are detected in the file
	 */
	static void loadEmployees(String file, ArrayList<Employee> list) throws FileNotFoundException,
	MatchingItemException{
		//initiate employee count
		int employeeCount = 0;

		//read text file and add items to employee array list
		try
		{
			BufferedReader reader = new BufferedReader(
					new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {

				line = line.trim();

				if (line.isEmpty() || line.startsWith("#")) {
					continue;
				}

				String[] lineSeg = line.split(",");

				for (int i = 0; i < lineSeg.length; i++) {
					lineSeg[i] = lineSeg[i].trim();
				}

				Employee newEmployee;

				//input validation
				if(lineSeg.length !=2) continue;
				if (!idPatternEmployee.matcher(lineSeg[0]).matches()) continue;

				newEmployee = new Employee(lineSeg[0],lineSeg[1]);

				//prevent user from adding a duplicate employee
				boolean duplicate = false;
				for (Employee existing : list) {
					if (existing.getEmployeeID().equalsIgnoreCase(lineSeg[0])) {
						duplicate = true;
						break;
					}
				}
				if (duplicate) {
					System.out.println("Skipping duplicate employee ID: " + lineSeg[0]);
					continue;
				}

				list.add(newEmployee);
				employeeCount++;

			}
			//display successful message with count
			System.out.println("Inventory loaded successfully! (" + employeeCount + " Employees)");
			reader.close();
		}


		catch(IOException ioe)
		{
			System.out.println(
					"An error occurred while reading the file: "
							+ ioe.getMessage());
		}
	}

	static String idToName(ArrayList<Employee> list, String target) {
		//call searchEmployee and store as variable
		int employee = EmployeeUtility.searchEmployee(list, target);
		//if employee cannot be found
		if (employee == -1) return "Unknown Employee";
		return list.get(employee).getEmployeeName();

	}
}