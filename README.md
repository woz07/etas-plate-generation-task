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
- Screenshot of linux

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

### File output
The program creates a file called `plates.txt` in the same directory as the executable or JAR. This file stores all previously generated plates and prevents duplicates.

# Assumptions
- Bla bla
- Bla bla
- Bla bla

# Development
This project was written in Java 11 using IntelliJ IDEA and built using:
- Maven-based `.jar` build
- GraavlVM Native Imag efor `.exe` generation
