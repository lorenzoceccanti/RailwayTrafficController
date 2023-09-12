@echo off
C:\prg\jdk8\bin\javac -classpath ".\*;C:\prg\libs\*" *.java
copy "ServerLogXML.class" ".."
copy "ValidatoreXML.class" ".."
copy "GestoreFile.class" ".."
del *.class
copy "..\ServerLogXML.class" ".\"
copy "..\ValidatoreXML.class" ".\"
copy "..\GestoreFile.class" ".\"
del "..\*.class"
C:\prg\jdk8\bin\java -classpath ".;C:\prg\libs\*" ServerLogXML
pause