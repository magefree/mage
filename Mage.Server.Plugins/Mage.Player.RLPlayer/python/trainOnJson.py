import gym
import torch
from torch import nn
import torch.nn.functional as F
import numpy as np
from hparams import hparams
from netComponents import play_game,Representer,MainNet

env=gym.make('gym_xmage:learnerVsRandom-v0')
converter=Representer() 
net=MainNet(hparams)
optimizer = torch.optim.Adam(net.parameters(), lr=hparams['lr'])
net.train()
for games in range(3000):
    (actions,observations,rewards)=play_game(net,converter,env,games%50==0)
    print(rewards[-1])
    optimizer.zero_grad()
    (out,critic)=net(observations)
    max_action_len=max([len(obs['action_mask']) for obs in observations])
    back_grad=torch.zeros((len(observations),max_action_len))
    entropy=torch.mean(out*torch.exp(out),dim=1,keepdim=True)*hparams["entropy_weight"]
    #Use neagive entropy becasue I want to increase it
    scalar_loss=entropy
    end_reward=rewards[-1] #For now, just use eposide rewards
    for i in range(len(actions)):
        back_grad[i,actions[i]]=-(rewards[i]-critic[i,0].detach()) #negate end reward to IMPROVE agent!
    critic_error=(torch.tensor(rewards).unsqueeze(dim=1)-critic)**2
    if(games%50==0):
        print(critic_error)
    scalar_loss=scalar_loss+critic_error
    out=torch.cat([out,scalar_loss],dim=1)
    back_grad=torch.cat([back_grad,torch.ones_like(scalar_loss)],dim=1)
    if(back_grad.shape[0]>hparams['batch_cutoff']):
        back_grad=back_grad*hparams['batch_cutoff']/back_grad.shape[0]
    out.backward(back_grad)
    #print(out.shape,back_grad.shape)
    optimizer.step()
from pathlib import Path
import pickle 
base_path=str(Path.home())+"/python/xmage/model_simple"
torch.save(net.state_dict(), base_path+".model")
with open(base_path+".converter", 'wb') as filehandler:
    pickle.dump(converter, filehandler)