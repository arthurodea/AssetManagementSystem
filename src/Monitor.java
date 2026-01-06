/**
 * Represents a computer monitor asset with display specifications.
 * Includes screen size in inches and resolution as unique attributes.
 * 
 * @author [Arthur O'Dea]
 * @version 1.0
 */
public class Monitor extends Asset{

	//unique attributes
	private double size;
	private String resolution;
	
	//getters and setters
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
	    if (size <= 0) throw new IllegalArgumentException("Size must be positive.");
	    this.size = size;
	}

	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	/**
     * Creates a new Monitor asset with specified display specifications.
     * Validates that screen size is a positive number.
     * 
     * @param itemID Unique asset identifier in format M### (e.g., "M001")
     * @param make Manufacturer brand name (e.g., "LG", "Dell", "ASUS")
     * @param model Model name or number (e.g., "UltraFine 27", "P2720D")
     * @param status Current availability status (IN_STOCK, ASSIGNED, or REPAIR)
     * @param assignedTo Employee ID if assigned, or empty string if not assigned
     * @param size Screen size in inches (must be positive)
     * @param resolution Display resolution (e.g., "4K", "1440p", "1080p")
     * @throws IllegalArgumentException If size is zero or negative
     */
	public Monitor(String itemID, String make, String model, AssetStatus status, String assignedTo, 
			double size, String resolution){
		super(itemID, make, model, status, assignedTo);
		setSize(size);
		setResolution(resolution);
	}

	@Override
	String getAssetDetails() {
		//returns asset details as a string
				return "Item ID: " + getAssetId()
	            + " Make: " + getMake()
	            + " Model: " + getModel()
	            + " Status: " + getStatus()
	            + " Assigned to: " + getAssignedTo()
	            + " Size(in): " + size
	            + " Resolution: " + resolution;
	}
	@Override
	public void checkOut() {
	}
	@Override
	public void returnItem() {
		setAssignedTo(""); // auto-normalizes to IN_STOCK in Asset
	}

}
