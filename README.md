# Asset Management System

A Java-based console application for managing IT assets (laptops, monitors, phones, software) and employee assignments.

## Features

- **Asset Management**: Track laptops, monitors, phones, and software licenses
- **Employee Management**: Add, remove, and search employees
- **Check Out/Return**: Assign assets to employees and track returns
- **Search & Sort**: Find assets by ID, make, or model; sort by various criteria
- **Data Persistence**: Load inventory and employees from text files
- **Security**: Software license keys are hashed using SHA-256

## Asset Types

- **Laptop**: RAM, storage, CPU specifications
- **Monitor**: Screen size, resolution
- **Phone**: Carrier, OS, storage
- **Software**: License key (hashed), expiration date

## Getting Started

### Prerequisites

- Java JDK 11 or higher
- Terminal/Command Line access

### Installation

1. Clone the repository:
```bash
git clone https://github.com/arthurodea/AssetManagementSystem.git
cd asset-management-system
```

2. Compile the project:
```bash
javac *.java
```

3. Run the application:
```bash
java AssetManagementSystem
```

## File Format

### inventory.txt
```
# Format: TYPE,ASSET_ID,BRAND,MODEL,STATUS,ASSIGNED_TO,...
LAPTOP,L001,Dell,XPS 13,IN_STOCK,,16,512,i7-1165G7
MONITOR,M001,LG,UltraFine 27,ASSIGNED,E001,27,4K
PHONE,P001,Apple,iPhone 14,IN_STOCK,,Verizon,iOS,512
SOFTWARE,S001,Microsoft,Office 365,ASSIGNED,E001,XXXXX-XXXXX-XXXXX,2026-05-01
```

### employees.txt
```
# Format: EMPLOYEE_ID, FIRST_NAME LAST_NAME
E001, John Doe
E002, Jane Smith
```

## Usage

The application provides a menu-driven interface with the following options:

1. Display All Items
2. Search Items (by Make, Model, or ID)
3. Sort Items (by Make, Model, or Type)
4. Check Out Item
5. Return Item
6. Add New Item
7. Remove Item
8. Display Available Items Only
9. Display Checked Out Items
10. Employee Management
11. Exit

## Project Structure
```
asset-management-system/
├── Asset.java                    # Abstract base class
├── Laptop.java                   # Laptop asset type
├── Monitor.java                  # Monitor asset type
├── Phone.java                    # Phone asset type
├── Software.java                 # Software license type
├── AssetStatus.java              # Enum for asset status
├── AssetLoader.java              # Loads inventory from file
├── AssetUtility.java             # Asset operations
├── Employee.java                 # Employee class
├── EmployeeLoader.java           # Loads employees from file
├── EmployeeUtility.java          # Employee operations
├── AssetManagementSystem.java    # Main application
├── Loanable.java                 # Interface for loanable items
├── SecurityHelper.java           # SHA-256 hashing utility
├── InvalidItemException.java     # Custom exception
├── MatchingItemException.java    # Custom exception
├── inventory.txt                 # Sample inventory data
└── employees.txt                 # Sample employee data
```

## Asset ID Format

- Laptops: `L###` (e.g., L001)
- Monitors: `M###` (e.g., M001)
- Phones: `P###` (e.g., P001)
- Software: `S###` (e.g., S001)
- Employees: `E###` (e.g., E001)

## Status Types

- **IN_STOCK**: Available for assignment
- **ASSIGNED**: Currently assigned to an employee
- **REPAIR**: Under maintenance/repair

## License

This project is open source and available under the [MIT License](LICENSE).

## Author

Arthur O'Dea

## Acknowledgments

Built as a project to demonstrate Java OOP concepts including:
- Inheritance and polymorphism
- File I/O operations
- Exception handling
- Data validation
- Security best practices
```
