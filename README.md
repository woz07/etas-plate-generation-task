# etas-plate-generation-task
This is my submission for etas plate generation task

# Features
- Generates valid and unique number plates using tags
- Automatically stores genereated plates to avoid duplication
- Lightweight and self-contained
- Cross-platform (via `.jar`) and native `.exe` (for windows)
- Optional `--debug` mode for development or troubleshooting

# Installation
### Option 1: Using .exe (Windows only)
- Download `epgt.exe` from the latest release
- Place it in a folder of it's own (it will generate `plates.txt` there once run)
- Run from terminal:
  ```
  ./epgt.exe
  ```
  <img src="https://github.com/user-attachments/assets/9d0857b1-c7bc-445f-be14-b2031d9b27b9" alt="win-ss" width="500" height="500"/>

### Option 2: Using .jar (Cross-platform)
- Ensure you have `java 11` installed (Amazon Corretto 11.0.24 recommended)
- Download `epgt.jar` from the latest release
- Run using:
  ```
  java -jar epgt.jar
  ```
  <img src="https://github.com/user-attachments/assets/3483f447-6b90-4ed9-9162-58c9f5c20d01" alt="lin-ss" width="500" height="500"/>

# Usage
### Default mode
Runs silently and saves generated plates to `plates.txt` in the current directory
```
./epgt.exe
# or
java -jar epgt.jar
```

### Debug mode
To see console output (generated plates and a range of different debug messages), add the `--debug` flag
```
epgt.exe --debug
# or
java -jar epgt.jar --debug
```
<img src="https://github.com/user-attachments/assets/0c921728-2581-4aa7-9299-de29cc226924" alt="win-ss-d" width="500" height="600"/>
<img src="https://github.com/user-attachments/assets/445ff38c-e09d-41a6-ba34-ee141bab7095" alt="lin-ss-d" width="500" height="600"/>

### File output
The program creates a file called `plates.txt` in the same directory as the executable or JAR. This file stores all previously generated plates and prevents duplicates.

# Assumptions
- The program is executed using either:
    - `epgt.exe` on **Windows 11**
    - `epgt.jar` on **Windows 11** or **Linux (Ubuntu-tested)** with **Java 11** (Amazon Corretto 11.0.24 recommended)
- The user has **read/write/create** permission in the directory from which the program is run
- The program is **run from a folder where it has access to create `plates.txt`** in the **same directory**
- If `plates.txt` does **not exist**, the program will **create it**
- If `plates.txt` **does exist**, the program will load previously generated plates to prevent duplicates
- `plates.txt` is a **plaintext file** with **one plate per line**
- The application is run in a **single threaded** context
- The program assumes **equal length arrays** for memory tags and date tags
- Date tags must follow the format: `dd/MM/yyyy`
- Memory tags must be **two uppercase alphabetic characters**
- Memory tags do **not** contain invalid characters (e.g numbers and symbols, etc)
- The plate format will follow: `{MEMORYTAG}{AGE}{THREE_RANDOM_LETTERS}`
- The 3 random letters **never include** the letters ‘I’ and ‘Q’, as per the problem specification
- If the `--debug` flag is passed, **console output is enabled** (e.g info, warnings and errors)
- Running the program multiple times from the **same folder** will ensure previously generated plates are retained
- The project is designed for **local terminal/command line execution only** (no GUI)

# Development
This project was written in Java 11 using IntelliJ IDEA and built using:
- Maven-based `.jar` build
- GraavlVM Native Imag efor `.exe` generation
