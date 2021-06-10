import gym
import torch
from torch import nn, scalar_tensor
import torch.nn.functional as F
import numpy as np
from netComponents import play_game,MainNet
from pathlib import Path
import pickle 
from hparams import hparams
import argparse
parser = argparse.ArgumentParser()
parser.add_argument("name")
args =parser.parse_args()
base_path=str(Path.home())+"/python/xmage/"+args.name
netDict=torch.load(base_path+".model")
with open(base_path+".converter", 'rb') as filehandler:
    converter=pickle.load(filehandler)
net=MainNet(hparams)
net.load_state_dict(netDict)
env=gym.make('gym_xmage:learnerVsRandom-v0')
play_game(net,converter,env,verbose=True)
