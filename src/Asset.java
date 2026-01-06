/**
 * Abstract base class representing a physical or digital asset in the inventory system.
 * All assets have an ID, make, model, status, and optional employee assignment.
 * Subclasses include Laptop, Monitor, Phone, and Software.
 * 
 * @author [Arthur O'Dea]
 * @version 1.0
 */
public abstract class Asset implements Loanable{
	protected String itemID;
	protected String make;
	protected String model;
	protected AssetStatus status;
	protected String assignedTo;

	//full constructor
	public Asset(String assetId, String brand, String model,
			AssetStatus status, String assignedTo) {

		this.itemID = assetId;
		this.make = brand;
		this.model = model;
		// normalize once on creation
		this.status = normalizeStatusFromAssignment(assignedTo, status);
		this.assignedTo = (this.status == AssetStatus.ASSIGNED) ? assignedTo : "";
	}

	//getters and setters
	public String getAssetId() {
		return itemID;
	}
	public void setAssetId(String assetId) {
		this.itemID = assetId;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String brand) {
		this.make = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public AssetStatus getStatus() {
		return status;
	}
	public void setStatus(AssetStatus status) {
		this.status = normalizeStatusFromAssignment(this.assignedTo, status);
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
	    if (assignedTo == null) assignedTo = "";
	    this.assignedTo = assignedTo.trim();
	    this.status = normalizeStatusFromAssignment(this.assignedTo, this.status);
	}


	//normalizes assetStatus to prevent impossible status
	protected static AssetStatus normalizeStatusFromAssignment(String assignedTo, AssetStatus status) {
		if (assignedTo == null) assignedTo = "";
		assignedTo = assignedTo.trim();

		if (!assignedTo.isEmpty()) return AssetStatus.ASSIGNED;
		if (status == AssetStatus.ASSIGNED) return AssetStatus.IN_STOCK;
		return status;
	}

	//abstract method for displaying asset details
	abstract String getAssetDetails();
}
