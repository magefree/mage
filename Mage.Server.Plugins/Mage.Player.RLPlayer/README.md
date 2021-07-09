Requirements: python3, pytorch
To train the AI, open two tabs in your terminal. 
In the first tab run:
cd python
python3 trainOnJson.py
In the second tab:
./run.sh

Instructions for playing against learned agent:
The script will periodically save models in 
$HOME_DIR/python/xmage/model_PPO(number)
Run playAgainst.py with the name of the saved model 
(For example) playAgainst.py model_PPO200
and open the Xmage server and client.
Select the RL AI from the dropdown lsit of AI choices. 
Choose /java/mage/Mage.Tests/RBTestAggro.dck as the deck for both players
You will be able to play a game against the AI 