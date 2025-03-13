# IT Asset Management Application

## 📌 Overview

This project is an **IT Asset Management Application** that retrieves information from network cards and performs connectivity tests. It is designed to help IT administrators monitor and manage network assets efficiently.

## 🚀 Features

- **Computer and Network Card Data Entry**: Users can manually enter computer and network card details and store them in a list.
- **Display Registered Computers**: The list of entered computers is displayed in a table.
- **Reset Functionality**: A "Reset" button allows users to clear the input fields.
- **Export Data**: The list of computers can be exported to a text file.
- **Retrieve Network Address**: Once a client and its network card are registered, the application can display the network address.
- **Connectivity Testing**: Users can perform a **ping test** on a network card using its IP address.
- **Import Computer List**: Users can import a list of computers from a text file.
- **Retrieve MAC Address**: Users can retrieve the MAC address corresponding to an IP address using the `arp.exe -a` command.

## 🛠️ Installation

To install and run the application, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/Paul-Loup-Germain/DEVA-Project.git
   ```

## 📂 Project Structure

```
📁 DEVA-Project/
│── 📁 JavaFXComputerApplication/                         # Project
    │── 📁 src/                                           # Source code
        │── 📁 main/                                      # Source code
            │── 📁 java/ch.etmles.srs/buisness            # Code application
                │── 📄 Computer.java
                │── 📄 NetworkCard.java                            
            │── 📁 java/ch.etmles.srs/view                # Code interface
                │── 📄 ComputerMngtApplication.java
                │── 📄 ComputerMngtController.java   
        │── 📁 test/                                      # Unit test
│── 📄 .gitignore                                         # file ignore
│── 📄 README.md                                          # Project documentation
```

## ✅ Usage

- Enter computer and network card details manually.
- View registered computers in a table.
- Use the reset button to clear input fields.
- Export the list of computers to a text file.
- Retrieve the network address of registered clients.
- Perform a ping test on a registered IP address.
- Import a list of computers from a file.
- Retrieve the MAC address linked to an IP address.

## 🤝 Contributions

Paul-Loup Germain & Diego Da-Silva

## 📜 License

This project is licensed under the **MIT License**.
