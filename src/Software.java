
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Represents a software license asset with license key and expiration date.
 * License keys are stored in hashed format for security using SHA-256.
 * 
 * @author [Arthur O'Dea]
 * @version 1.0
 */
public class Software extends Asset{

	//unique attributes
	private String licenseKeyRaw;
	private String licenseKeyHash;
	private LocalDate expireDate;
	private static final Pattern licenceKeyPattern = Pattern.compile("^[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}$");
	
	//getters and setters
	public String getLicenceKey() {
		return licenseKeyRaw;
	}
	public void setLicenceKey(String licenceKey) {
		this.licenseKeyRaw = licenceKey;
	}
	public LocalDate getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

    /**
     * Creates a new Software asset with license key and expiration date.
     * The license key is validated for proper format (XXXXX-XXXXX-XXXXX) and stored
     * as a SHA-256 hash for security. The raw key is normalized to uppercase.
     * 
     * @param itemID Unique asset identifier in format S### (e.g., "S001")
     * @param make Software publisher or vendor (e.g., "Microsoft", "Adobe", "JetBrains")
     * @param model Software product name (e.g., "Office 365", "Creative Cloud")
     * @param status Current availability status (IN_STOCK, ASSIGNED, or REPAIR)
     * @param assignedTo Employee ID if assigned, or empty string if not assigned
     * @param licenceKey License key in format XXXXX-XXXXX-XXXXX (alphanumeric)
     * @param expireDate Date when the license expires (ISO format: YYYY-MM-DD)
     * @throws IllegalArgumentException If license key format is invalid, or if key or date is null
     */
	public Software(String itemID, String make, String model, AssetStatus status, String assignedTo,
	        String licenceKey, LocalDate expireDate) {

	    super(itemID, make, model, status, assignedTo);

	    if (licenceKey == null) throw new IllegalArgumentException("License key cannot be null");
	    if (expireDate == null) throw new IllegalArgumentException("Expiration date cannot be null");

	    // normalize + validate key
	    licenceKey = licenceKey.trim().toUpperCase();

	    if (!licenceKeyPattern.matcher(licenceKey).matches()) {
	        throw new IllegalArgumentException("Invalid license key format");
	    }

	    this.licenseKeyRaw = licenceKey;
	    this.licenseKeyHash = SecurityHelper.sha256_hash(this.licenseKeyRaw);

	    // set date ONCE (use setter OR direct assignment)
	    setExpireDate(expireDate);
	}

	@Override
	String getAssetDetails() {
		//returns asset details as a string
		return "Item ID: " + getAssetId()
        + " Make: " + getMake()
        + " Model: " + getModel()
        + " Status: " + getStatus()
        + " Assigned to: " + getAssignedTo() + 
        " License Key: " + licenseKeyHash
	    + " Expiration Date: " + expireDate;
	}
	@Override
	public void checkOut() {
	}
	@Override
	public void returnItem() {
		setAssignedTo(""); // auto-normalizes to IN_STOCK in Asset
	}

}
