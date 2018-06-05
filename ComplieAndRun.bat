@echo off
title Compile java code!
cd C:\Users\Eli\eclipse-workspace\MCO_Project\MyDistributedServer\src
javac *.java
echo Done comple code!

@echo off
title Run program!
cd C:\Users\Eli\eclipse-workspace\MCO_Project\MyDistributedServer\src
start cmd.exe /k "echo --MASTER --  & java Master 100"

start cmd.exe /k "echo --SLAVE 01-- & java Slave  200 1"
start cmd.exe /k "echo --SLAVE 02-- & java Slave  300 2"
start cmd.exe /k "echo --SLAVE 03-- & java Slave  400 3"
start cmd.exe /k "echo --SLAVE 04-- & java Slave  500 4"
start cmd.exe /k "echo --CLIENT-- & java Client"
pause
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
pause
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
pause
#12
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
start cmd.exe /k "echo --CLIENT-- & java Client"
#60

echo Done !
