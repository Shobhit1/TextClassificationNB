Name : Shobhit Agarwal
Net ID: sxa138130

Extract sxa138130. 
From the command prompt navigate to /agarwal_sxa138130/MachineLearningHW2/src and then run the following commands

javac ml/hw2/*.java

and then run the following command to execute:
1.) To execute the naive bayes classifier on folder “train” and test on folder “train”
	•	without using stop words :	
	⁃	java ml.hw2.DriverHW2 train test no 1 100 0.5
	•	using stop words: 
	⁃	java ml.hw2.DriverHW2 train test yes 1 100 0.5
2.) To execute the logistic regression classifier on folder “train” and testing on folder “testing”
	•	without using stop words :	
	⁃	java ml.hw2.DriverHW2 train test no 2 100 0.5
	•	using stop words: 
	⁃	java ml.hw2.DriverHW2 train test yes 2 100 0.5

Here the number 1 & 2 differentiates between the classifiers.

To change the number of iterations - change the ‘100’ in the above command to change the number of iterations for logistic regression

To change the value of lambda - change the value ‘0.5’ to the value of your choice


To change the data set to test the program on : 
1. Copy the files to /agarwal_sxa138130/MachineLearningHW2/src folder and then pass the name of the folders as parameter to above execution command.


Some execution examples:

java ml.hw2.DriverHW2 train test no 1 1000 0.5 - Testing naive bayes without using stop words

java ml.hw2.DriverHW2 train test yes 1 10 0.5 - Testing naive bayes using stop words

java ml.hw2.DriverHW2 train test no 2 100 0.5 - Testing logistic regression without using stop words ,iterations: 100 and lambda : 0.5
java ml.hw2.DriverHW2 train test yes 2 100 0.5 -Testing logistic regression using stop words ,iterations: 100 and lambda : 0.5