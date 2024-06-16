cd src/wfc
javac -d ../../build *.java pattern/*.java
cd ../../build
jar cf libWFC.jar .