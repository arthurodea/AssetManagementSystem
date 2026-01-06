/**
 * Represents a laptop computer asset with hardware specifications.
 * Includes RAM capacity, storage capacity, and CPU model as unique attributes.
 * 
 * @author [Arthur O'Dea]
 * @version 1.0
 */
public class Laptop extends Asset{

	//unique attributes
	private int ramGB;
	private int storageGB;
	private String CPU;

	//getters and setters

	public int getRamGB() {
		return ramGB;
	}
	public void setRamGB(int ramGB) {
	    if (ramGB < 0 || ramGB > 256) throw new IllegalArgumentException("Invalid RAM");
	    this.ramGB = ramGB;
	}

	public void setStorageGB(int storageGB) {
	    if (storageGB < 0 || storageGB > 8000) throw new IllegalArgumentException("Invalid storage");
	    this.storageGB = storageGB;
	}

	public int getStorageGB() {
		return storageGB;
	}
	
	public String getCPU() {
		return CPU;
	}
	public void setCPU(String cPU) {
		CPU = cPU;
	}

	/**
     * Creates a new Laptop asset with specified hardware specifications.
     * Validates that RAM is between 0-256 GB and storage is between 0-8000 GB.
     * 
     * @param itemID Unique asset identifier in format L### (e.g., "L001")
     * @param make Manufacturer brand name (e.g., "Dell", "Apple", "Lenovo")
     * @param model Model name or number (e.g., "XPS 13", "MacBook Air M2")
     * @param status Current availability status (IN_STOCK, ASSIGNED, or REPAIR)
     * @param assignedTo Employee ID if assigned, or empty string if not assigned
     * @param ramGB RAM capacity in gigabytes (valid range: 0-256)
     * @param storageGB Storage capacity in gigabytes (valid range: 0-8000)
     * @param CPU Processor model identifier (e.g., "i7-1165G7", "M2")
     * @throws IllegalArgumentException If RAM or storage values are out of valid range
     */
	public Laptop(String itemID, String make, String model, AssetStatus status, 
			String assignedTo, int ramGB, int storageGB, String CPU) {
		super(itemID, make, model, status, assignedTo);
		setRamGB(ramGB);
		setStorageGB(storageGB);
		setCPU(CPU);

	}

	@Override
	String getAssetDetails() {
		//returns asset details as a string
		return "Item ID: " + getAssetId()
        + " Make: " + getMake()
        + " Model: " + getModel()
        + " Status: " + getStatus()
        + " Assigned to: " + getAssignedTo()
        + " RAM(GB): " + ramGB
        + " Storage(GB): " + storageGB
        + " CPU: " + CPU;
	}
	@Override
	public void checkOut() {
	}
	@Override
	public void returnItem() {
		setAssignedTo(""); // auto-normalizes to IN_STOCK in Asset
	}
	

}
