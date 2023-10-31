# Overview
This project serves as purpose for solving an example problem related to Distributed Random Number Generator.
## Problem
```
Implement the method nextNum() and a minimal but effective set of unit tests. Implement in the language of your choice, Python is preferred,
but Java and other languages are completely fine. Make sure your code is exemplary, as if it was going to be shipped as part of a production
system.
As a quick check, given Random Numbers are [-1, 0, 1, 2, 3] and Probabilities are [0.01, 0.3, 0.58, 0.1, 0.01] if we call nextNum() 100 times
we may get the following results. As the results are random, these particular results are unlikely.
-1: 1 times
0: 22 times
1: 57 times
2: 20 times
3: 0 times
```
## Solution
The DistributionRandomNumberGenerator solves the above problem
by first accepting a valid map(which is generated based on a validated numbers and probability lists)
of distributed numbers,
pre-calculates the cumulative probabilities during initialization
and then generates random number based on the distribution provided.

Project also contains Demo class which has a main method for testing out the Generator.
It can accept console input of any random numbers and probabilities
and perform random number generation based on the distribution. 

## Usage
```
// Initialize the number generator based on the numbers distribution map (allowed numbers and their probabilities)
RandomNumberGenerator numberGenerator = new DistributionRandomNumberGenerator(numbersDistribution);
numberGenerator.nextNum();
```

