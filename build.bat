
@echo off

cd bin
del *.class
cd ..

javac -d bin -cp bin ^
  src/gui/*.java src/gui/panels/*.java src/gui/controls/*.java ^
  src/game/*.java src/game/sprites/*.java src/game/world/*.java ^
  src/util/*.java src/util/resources/*.java src/util/math/*.java