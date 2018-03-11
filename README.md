# GWU CSCI6511 Artificial Intelligence Term Project

## Project 1 

### (1) solve maze 

Read maze from a txt file and tell whether it has a path given a start point and an end point, using backtracking algorithm. This is an example of uninformed search.

### (2) Tic Tac Toe game

A simple reflect agent. 

## Project 2: generalized Tic Tac Toe game 

### Board size 

m * m board

### Winning condition 

n chess in a row

### Algorithm 

using minimax algorithm with alpha-beta prunning

### Evaluation function 

We use a evaluation method of counting winning windows, which definition can be found in [this article](https://web.stanford.edu/class/cs221/2017/restricted/p-final/xiaotihu/final.pdf). In addition, we make some improvements. We count the max value of continuous chess in a window and regard it as a bonus to the score of the window. This bonus coeffience will encourage the AI to form continuous chess or block enemy's continuous chess in the process of the game.

### Optimizations 

According to the definition of winning window, a chess placed on the board can only effect the score of the windows that contains the chess. Therefore, we pass the board score down with the recursion and update the board score according to the partial difference, 
this avoid unnecessary evaluation and save computation time. Furthermore, we limit the search space, the space can be set for different player. The search space is selected from the most valuable places from the board. 

### Acknowledgement

Special thanks to [@ZjutMisLeo](https://github.com/ZjutMisLeo) for integrating a REST API into this project and enable us to compete with other teams in the class. 
