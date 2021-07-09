import gym
import torch
from torch import nn
import torch.nn.functional as F
import numpy as np
from torch.utils.tensorboard import SummaryWriter
from hparams import hparams
from netComponents import play_game,Representer,MainNet
from pathlib import Path
import pickle 

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
def collect_experience(min_actions,env,net,converter,opponent_net=None):
    actions=[]
    observations=[]
    rewards=[]
    weights=[]
    net.eval()
    while(len(actions)<min_actions):
        global game_counter
        (a,o,r)=play_game(net,converter,env,game_counter%50==0,opponent_net)
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
    (actions,observations,rewards,weights)=collect_experience(hparams['exp_per_batch'],env,net,converter)
    with torch.no_grad():
        (base_log_prob,critic_base)=net(observations)
        base_log_prob=base_log_prob.detach()
    net.train()
    max_action_len=max([len(obs['action_mask']) for obs in observations])
    for i in range(hparams['steps_per_batch']):
        optimizer.zero_grad()
        (out,critic)=net(observations)
        advantage=torch.zeros((len(observations),max_action_len))
        #Use neagive entropy becasue I want to increase it
        entropy=torch.mean(out*torch.exp(out),dim=1,keepdim=True)*hparams["entropy_weight"]
        scalar_loss=entropy
        critic_target=torch.tensor(rewards).unsqueeze(dim=1)
        critic_error=(critic-critic_target)**2
        scalar_loss=scalar_loss+critic_error*hparams['critic_weight']
        unnormalized_adv=torch.tensor(rewards)-critic_base.squeeze()
        norm_adv=unnormalized_adv-torch.mean(unnormalized_adv)
        norm_adv=norm_adv/(torch.std(norm_adv)+1e-6)
        for i in range(len(actions)):
            advantage[i,actions[i]]=norm_adv[i] #negate end reward to IMPROVE agent!
        action_ratio=torch.exp(out-base_log_prob)
        unclipped=action_ratio*advantage
        clipped=torch.clamp(action_ratio,1-hparams["PPOeps"],1+hparams["PPOeps"])*advantage
        out=torch.min(unclipped,clipped)
        out=-torch.sum(out,dim=1)
        scalar_loss=torch.mean(scalar_loss)
        print(torch.mean(out),torch.mean(scalar_loss))
        out=out+scalar_loss
        #out=out*torch.tensor(weights).unsqueeze(dim=-1)
        out=torch.mean(out)
        out.backward()
        torch.nn.utils.clip_grad_norm_(net.parameters(), hparams['grad_clip'])
        #print(out.shape,back_grad.shape)
        optimizer.step()
    if(games%50==0):
        base_path=str(Path.home())+"/python/xmage/model_PPO"+str(games)
        torch.save(net.state_dict(), base_path+".model")
        with open(base_path+".converter", 'wb') as filehandler:
            pickle.dump(converter, filehandler)

