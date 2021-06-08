import gym
import torch
from torch import nn
import torch.nn.functional as F
import numpy as np
from torch.utils.tensorboard import SummaryWriter
from hparams import hparams
from netComponents import play_game,Representer,MainNet

env=gym.make('gym_xmage:learnerVsRandom-v0')
converter=Representer() 
net=MainNet(hparams)
optimizer = torch.optim.AdamW(net.parameters(), lr=hparams['lr'])
net.train()
location="runs/current"
import shutil
shutil.rmtree(location)
writer = SummaryWriter(log_dir=location)
game_counter=0
def collect_experience(min_actions,env,net,converter):
    actions=[]
    observations=[]
    rewards=[]
    weights=[]
    while(len(actions)<min_actions):
        global game_counter
        (a,o,r)=play_game(net,converter,env,game_counter%50==0)
        actions+=a
        observations+=o
        rewards+=r
        writer.add_scalar('Reward', rewards[-1], game_counter)
        print(rewards[-1])
        weighting=min(1/len(actions),1/hparams['batch_cutoff'])
        weights=len(actions)*[weighting]
        game_counter+=1
    return (actions,observations,rewards,weights)
for games in range(10000):
    (actions,observations,rewards,weights)=collect_experience(300,env,net,converter)
    optimizer.zero_grad()
    (out,critic)=net(observations)
    max_action_len=max([len(obs['action_mask']) for obs in observations])
    back_grad=torch.zeros((len(observations),max_action_len))
    #Use neagive entropy becasue I want to increase it
    entropy=torch.mean(out*torch.exp(out),dim=1,keepdim=True)*hparams["entropy_weight"]
    scalar_loss=entropy
    critic_pred=torch.tanh(critic)
    for i in range(len(actions)):
        back_grad[i,actions[i]]=-(rewards[i]-critic_pred[i,0].detach()) #negate end reward to IMPROVE agent!
    critic_target=(torch.tensor(rewards).unsqueeze(dim=1)+1)/2
    critic_error=F.binary_cross_entropy_with_logits(critic,critic_target,reduction="none")
    scalar_loss=scalar_loss+critic_error*hparams['critic_weight']
    out=torch.cat([out,scalar_loss],dim=1)
    back_grad=torch.cat([back_grad,torch.ones_like(scalar_loss)],dim=1)
    grad_weight=torch.tensor(weights).unsqueeze(dim=-1)
    back_grad=back_grad*grad_weight
    out.backward(back_grad)
    torch.nn.utils.clip_grad_norm_(net.parameters(), hparams['grad_clip'])
    #print(out.shape,back_grad.shape)
    optimizer.step()
from pathlib import Path
import pickle 
base_path=str(Path.home())+"/python/xmage/model_simple"
torch.save(net.state_dict(), base_path+".model")
with open(base_path+".converter", 'wb') as filehandler:
    pickle.dump(converter, filehandler)