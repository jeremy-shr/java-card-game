This is The README file detailig how to run the test suite for our Multi-threaded card game

To execute the test suite :

- locate yourself in the testing directory
- all the files have been compiled with the appropriate classpaths, so no need to compile anything 
- run the command : java -cp .\lib\junit-4.13.2.jar`;`.\lib\hamcrest-core-1.3.jar.`;`.`; TestRunner.java
(the command above should run the thest suite with Junit4, hamcrest and all the game files as a classPath)

- if you get true returned in terminal, all the tests have been executed without issue
else : 
-if you get expected values compared to current values and false returned at the end, there was a issue with one of the tests 
