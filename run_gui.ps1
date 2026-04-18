javac -cp ".;lib/flatlaf-3.7.1.jar" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp src;lib/flatlaf-3.7.1.jar com.expertsystem.MainApp --gui
