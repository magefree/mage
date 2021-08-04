Usage: 
Set the deck the AI is to be trained on in config.properties 
Train by running ./run.sh

Once you have trained and AI, start up the server and client. Select RLPlayer from the AI selection menu 
and choose the deck you trained on for both you and the computer player

Implemented: Training with AAC, save, restore for playing against
TODO:
The network is extremely basic, update it to a better architechture and take into account all inputs

Add more advanced autoselection so the AI can learn to play more decks. For now
it can only learn to play one deck at a time

NOTE! Due to me only having java 14 on my computer this branch carries compatability patches
These should either be removed before merging or this brach should be made to depend
on the branch with them added