# CSCI6511Project

## Project 1 

### (1) solve maze 

### (2) Tic Tac Toe game

## Project 2: generalized Tic Tac Toe game 

### Board size 

m * m board

### Winning condition 

n chess in a row

### Algorithm 

using minimax algorithm with alpha-beta prunning

### Evaluation function 

I use a evaluation method of counting winning windows, which definition can be found in [this article](https://web.stanford.edu/class/cs221/2017/restricted/p-final/xiaotihu/final.pdf). In addition, I make some improvements. I count the max value of continuous chess in a window and regard it as a bonus to the score of the window. This bonus coeffience will encourage the ai to form continuous chess or block enemy's continuous chess in the process of the game.

### Optimizations 

I pass the board score down with the recursion and update the board score according to the partial difference, 
this avoid unnecessary evaluation and save computation time. Furthermore, I limit the search space, the space can be set for different player. The search space is selected from the most valuable places from the board. When setting the depth to 4 and the search space to 16, the ai can return the result in 1 second in a 15 * 15, 5 in a row game.
