
public  class Employee{
	protected String employeeId;
	protected String employeeName;
	
	//full constructor
	public Employee(String employeeID, String employeeName) {
		this.employeeId = employeeID;
		this.employeeName = employeeName;
	}
	
	//getters and setters
	public String getEmployeeID() {
		return employeeId;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeId = employeeID;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	//returns employee id and name as a string 
	public String getEmployeeDetails() {
		return "ID: " + employeeId+ " Name: " + employeeName;
				
	}
	
}
