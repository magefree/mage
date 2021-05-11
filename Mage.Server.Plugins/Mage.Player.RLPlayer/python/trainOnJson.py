import gym
import torch
from torch import nn
import torch.nn.functional as F
import numpy as np
env=gym.make('gym_xmage:learnerVsRandom-v0')
from hparams import hparams
class Representer():
    def __init__(self):
        self.names_to_ints=dict()
        self.count=1
    #Return list of IDs, list of action IDs 
    def convert(self,obs):
        game=obs['game']
        reals=game['reals']
        flat_reals=self.flatten_reals(reals)
        perms=game['permanents']
        (cardIDs,card_nums)=self.id_perms(perms)
        actionIDs=self.id_actions(obs['actions'])
        result={
            "reals":flat_reals,
            "cardIDs":cardIDs,
            "card_nums":card_nums,
            "actionIDs":actionIDs
        }
        print(result)
        return result
    def get_num(self,cardString):
        if(cardString in self.names_to_ints):
            return self.names_to_ints[cardString]
        else:
            self.count+=1
            self.names_to_ints[cardString]=self.count
            return self.count
    def id_actions(self,actions):
        L=[]
        max_actions=2
        for act in actions:
            key_count=0
            act_repr=[]
            for key in act:
                key_count+=1
                ID=self.get_num("Action-"+key+str(act[key]))
                act_repr.append(ID)
            for i in range(max_actions-key_count):
                act_repr.append(self.get_num("Blank Action"))
            L.append(act_repr)
        return L
    def id_perms(self,perms):
        id_L=[]
        reals_L=[]
        for obj in perms:
            card_string=obj['controller']+"-"+obj['name']
            embedID=self.get_num(card_string)
            id_L.append(embedID)
            card_nums=(obj['power'],obj['toughness'])
            reals_L.append(card_nums)
        return (id_L,reals_L)
    def flatten_reals(self,reals):
        L=[]
        for val in reals.values():
            if(type(val)==int):
                L.append(val)
            elif(type(val)==dict):
                L.extend(self.flatten_reals(val))
            else:
                return 1/0
        return L
converter=Representer() 
def pad_to_numpy(arr):
    if(len(arr)==0):
        return arr
    if(len(arr[0])==0):
        return arr
    numpied=[np.array(val) for val in arr]
    shapes=[np.array(val.shape) for val in numpied]
    max_size=np.maximum.reduce(shapes)
    for i in range(len(arr)):
        pads=[(0,max_size[j]-numpied[i].shape[j]) for j in range(len(max_size))]
        numpied[i]=np.pad(numpied[i],pads,'constant', constant_values=0)
    ret=np.array(numpied)
    return ret
def pad_dicts(dicts):
    for key in dicts:
        dicts[key]=pad_to_numpy(dicts[key])
#Combines a list of dicts with the same keys into a dict of lists
def merge_dicts(dicts):
    res={}
    for key in dicts[0]:
        L=[]
        for dictionary in dicts:
            L.append(dictionary[key])
        res[key]=L
    pad_dicts(res)
    return res
class Preparer(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.embed=nn.Embedding(hparams['max_represent'],hparams['embed_dim'])
        self.hparams=hparams
    def forward(self,data):
        data=merge_dicts(data)
        reals=torch.tensor(data['reals'],dtype=torch.float32)
        card_embed=self.embed(torch.tensor(data['cardIDs'],dtype=torch.long))
        card_nums=torch.tensor(data['card_nums'],dtype=torch.float32)
        if(card_nums.shape[1]==0):
            card_nums=torch.reshape(card_nums,[*card_nums.shape,hparams['num_card_reals']])
        card_all=torch.cat([card_embed,card_nums],dim=2)
        action_embed=self.embed(torch.tensor(data['actionIDs'],dtype=torch.long))
        shape=action_embed.shape
        action_embed=action_embed.reshape(shape[0],shape[1],shape[2]*shape[3])
        return (reals,card_all,action_embed)
class LinNet(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams
        act_dim=hparams["num_card_reals"]*hparams[ "embed_dim"]
        self.act_linear=nn.Linear(act_dim,hparams["dot_dim"])
        card_dim=hparams[ "embed_dim"]+hparams["num_card_reals"]
        self.cards_linear=nn.Linear(card_dim,hparams["dot_dim"])
        self.reals_linear=nn.Linear(hparams["num_reals"],hparams["dot_dim"])
        self.reals_norm=nn.BatchNorm1d(hparams["num_reals"])
        self.cards_norm=nn.BatchNorm1d(card_dim)
        self.act_norm=nn.BatchNorm1d(act_dim)
    def forward(self,reals,card_all,action_embed):
        reals=self.reals_norm(reals)
        reals=self.reals_linear(reals)
        if(card_all.shape[1]>0):
            cards=torch.sum(card_all,dim=1)
            cards=self.cards_norm(cards)
            cards=self.cards_linear(cards)
            reals=reals+cards
        action_embed=action_embed.permute(0,2,1)
        action_embed=self.act_norm(action_embed)
        action_embed=action_embed.permute(0,2,1)
        actions=self.act_linear(action_embed)
        reals=torch.unsqueeze(reals,dim=2)
        pred_values=actions@reals
        pred_values=torch.squeeze(pred_values,dim=2)
        print(pred_values.shape)
        print(pred_values)
        return pred_values
prep=Preparer(hparams)
net=LinNet(hparams)
net.eval()
for games in range(5):
    observation = env.reset()
    actions=[]
    observations=[]
    
    while(True):
        env.render()
        # your agent here (this takes random actions)
        action = env.sample_obs(observation)
        observation, reward, done, info = env.step(action)
        converted=converter.convert(observation)
        if(len(converted['actionIDs'])>0):
            (reals,card_all,action_embed)=prep([converted])
            net(reals,card_all,action_embed)
        if done:
            env.render()
            break