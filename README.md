# cse-110-project-team-6

1. Open the project in VSCode.
   
2. Configure launch.json to look like this and replace <PATH_TO_JAVAFX_JARS> with the filepath to the location of the JAVAFX files from lab 1:
   
   "version": "0.2.0",
   "configurations": [
        {
            "type": "java",
            "name": "PantryPal",
            "request": "launch",
            "mainClass": "pantrypal.PantryPal",
            "vmArgs": "--module-path '<PATH_TO_JAVAFX_JARS>' --add-modules javafx.controls,javafx.fxml"
        },
        {
            "type": "java",
            "name": "ppserver",
            "request": "launch",
            "mainClass": "ppserver.PPServer"
        }
    ]

3. Start ppserver using VSCode's run button. PantryPal will not work if ppserver is not running.
   
4. Start PantryPal using VSCode's run button.
