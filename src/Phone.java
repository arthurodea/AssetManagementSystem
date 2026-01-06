/**
 * Represents a mobile phone asset with carrier and operating system details.
 * Includes carrier network, operating system, and storage capacity as unique attributes.
 * 
 * @author [Arthur O'Dea]
 * @version 1.0
 */
public class Phone extends Asset{
	//unique attributes
	private String carrier;
	private String OS;
	private int storageGB;


	//getters and setters
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		this.OS = oS;
	}
	public void setStorageGB(int storageGB) {
	    if (storageGB < 0 || storageGB > 8000) throw new IllegalArgumentException("Invalid storage");
	    this.storageGB = storageGB;
	}

	public int getStorageGB() {
		return storageGB;
	}
	
	/**
     * Creates a new Phone asset with specified mobile device details.
     * Validates that storage is between 0-8000 GB.
     * 
     * @param itemID Unique asset identifier in format P### (e.g., "P001")
     * @param make Manufacturer brand name (e.g., "Apple", "Samsung", "Google")
     * @param model Model name or number (e.g., "iPhone 14", "Galaxy S23", "Pixel 8")
     * @param status Current availability status (IN_STOCK, ASSIGNED, or REPAIR)
     * @param assignedTo Employee ID if assigned, or empty string if not assigned
     * @param carrier Mobile carrier network (e.g., "Verizon", "AT&amp;T", "T-Mobile")
     * @param OS Operating system (e.g., "iOS", "Android")
     * @param storageGB Storage capacity in gigabytes (valid range: 0-8000)
     * @throws IllegalArgumentException If storage value is out of valid range
     */
	public Phone(String itemID, String make, String model, AssetStatus status, String assignedTo, 
			String carrier, String OS, int storageGB){
		super(itemID, make, model, status, assignedTo);
		setCarrier(carrier);
		setOS(OS);
		setStorageGB(storageGB);

	}

	@Override
	String getAssetDetails() {
		//returns asset details as a string
				return "Item ID: " + getAssetId()
	            + " Make: " + getMake()
	            + " Model: " + getModel()
	            + " Status: " + getStatus()
	            + " Assigned to: " + getAssignedTo()
	            + " Carrier: " + carrier
	            + " Operating System: " + OS
	            + " Storage(GB): " + storageGB;
	}
	@Override
	public void checkOut() {
	}
	@Override
	public void returnItem() {
		setAssignedTo(""); // auto-normalizes to IN_STOCK in Asset
	}
}
