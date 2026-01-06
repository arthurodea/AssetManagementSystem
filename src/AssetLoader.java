import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AssetLoader {
	/**
	 * Loads asset inventory data from a text file and populates the inventory list.
	 * Supports multiple asset types: Laptop, Monitor, Phone, and Software.
	 * Automatically skips empty lines, comments (lines starting with #), and invalid entries.
	 * 
	 * @param file Path to the inventory text file (e.g., "inventory.txt")
	 * @param inv ArrayList to populate with loaded Asset objects
	 * @throws FileNotFoundException If the specified file cannot be found
	 * @throws MatchingItemException If duplicate asset IDs are detected in the file
	 */
	static void loadInventory(String file, ArrayList<Asset> inv) throws FileNotFoundException, 
	MatchingItemException{
		//Initiate item count
		int itemCount = 0;

		//read text file and add items to asset array list
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

				Asset newAsset;

				if (lineSeg[0].equalsIgnoreCase("laptop")) {
					// expect 8 fields total
					if (lineSeg.length != 9) continue;

					//gets string value of AssetStatus
					AssetStatus status = AssetStatus.valueOf(lineSeg[4].trim().toUpperCase());

					newAsset = new Laptop(
							lineSeg[1], lineSeg[2], lineSeg[3],
							status, lineSeg[5],
							Integer.parseInt(lineSeg[6]), Integer.parseInt(lineSeg[7]), lineSeg[8]
							);

				} else if (lineSeg[0].equalsIgnoreCase("monitor")) {
					if (lineSeg.length != 8) continue;

					//gets string value of AssetStatus
					AssetStatus status = AssetStatus.valueOf(lineSeg[4].trim().toUpperCase());

					newAsset = new Monitor(
							lineSeg[1], lineSeg[2], lineSeg[3],
							status, lineSeg[5],
							Double.parseDouble(lineSeg[6]), lineSeg[7]
							);

				} else if (lineSeg[0].equalsIgnoreCase("phone")) {
					if (lineSeg.length != 9) continue;

					//gets string value of AssetStatus
					AssetStatus status = AssetStatus.valueOf(lineSeg[4].trim().toUpperCase());

					newAsset = new Phone(
							lineSeg[1], lineSeg[2], lineSeg[3],
							status, lineSeg[5],
							lineSeg[6], lineSeg[7], Integer.parseInt(lineSeg[8])
							);

				} else if (lineSeg[0].equalsIgnoreCase("software")) {
					if (lineSeg.length != 8) continue;

					//gets string value of AssetStatus
					AssetStatus status = AssetStatus.valueOf(lineSeg[4].trim().toUpperCase());

					//prevents invalid expiry date
					LocalDate expiryDate;
					try {
						expiryDate = LocalDate.parse(lineSeg[7].trim()); 
					} catch (Exception e) {
						System.out.println("Skipping SOFTWARE with invalid expiry date: " + line);
						continue;
					}

					newAsset = new Software(
							lineSeg[1], lineSeg[2], lineSeg[3],
							status, lineSeg[5],
							lineSeg[6], LocalDate.parse(lineSeg[7])
							);

				} else {
					continue;
				}

				inv.add(newAsset);
				itemCount++;
			}

			//display successful message with count
			System.out.println("Inventory loaded successfully! (" + itemCount + " items)");
			reader.close();
		}
		catch(IOException ioe) 
		{
			System.out.println(
					"An error occurred while reading the file: "
							+ ioe.getMessage());
		}
	}
}
