import gym
import torch
from torch import nn
import torch.nn.functional as F
import numpy as np
from hparams import hparams
class Representer():
    def __init__(self):
        self.names_to_ints=dict()
        self.count=1
    #Return list of IDs, list of action IDs 
    def convert(self,obs,verbose=False):
        
        game=obs['game']
        reals=game['reals']
        flat_reals=self.flatten_reals(reals)
        perms=game['permanents']
        (cardIDs,card_nums)=self.id_perms(perms)
        actionIDs=self.id_actions(obs['actions'])
        action_mask=[1]*len(obs['actions'])
        result={
            "reals":flat_reals,
            "cardIDs":cardIDs,
            "card_nums":card_nums,
            "actionIDs":actionIDs,
            "action_mask":action_mask,
        }
        if(verbose):
            print(obs['actions'])
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
def pad_to_numpy(arr):
    if(len(arr)==0):
        return arr
    numpied=[np.array(val) for val in arr]
    shapes=[val.shape for val in numpied if val.shape!=(0,)]
    #print(shapes)
    if(len(shapes)==0):
        max_size=(0,)
    else:
        max_size=np.maximum.reduce(shapes)
    for i in range(len(arr)):
        numpied[i].resize(max_size)
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
class InputNorm(nn.Module):
    def __init__(self,hparams,shape,mean_dims):
        super().__init__()
        shape=[1]+list(shape)
        self.mean_dims=mean_dims
        self.avg=nn.Parameter(torch.zeros(shape))
        self.variance=nn.Parameter(torch.ones(shape))
        self.avg.requires_grad=False
        self.variance.requires_grad=False
        self.iterations=nn.Parameter(torch.tensor(1.0),requires_grad=False)
        self.max_iter=hparams['decay_steps']
    def forward(self,x):
        if (self.training and x.shape[0]==1): #Only fit parameters when exploring
            detached_x=x.detach()
            detached_x=torch.mean(detached_x,dim=self.mean_dims,keepdim=True)
            iter=float(min(self.max_iter,self.iterations+1))
            self.iterations=nn.Parameter(torch.tensor(iter),requires_grad=False)
            mult=1/self.iterations
            self.avg=nn.Parameter((1-mult)*self.avg+mult*detached_x)
            squared_diff=(detached_x-self.avg)**2
            mult=1/(self.iterations+1)
            self.variance=nn.Parameter((1-mult)*self.variance+mult*squared_diff)
        x=x-self.avg
        x=x/torch.sqrt(self.variance)
        return x
    
class Preparer(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.embed=nn.Embedding(hparams['max_represent'],hparams['embed_dim'])
        self.norm_reals=InputNorm(hparams,[hparams['num_reals']],0)
        self.norm_cards=InputNorm(hparams,[1,hparams['num_card_reals']],1)
        self.hparams=hparams
    def forward(self,data):
        data=merge_dicts(data)
        reals=torch.tensor(data['reals'],dtype=torch.float32)
        reals=self.norm_reals(reals)
        card_embed=self.embed(torch.tensor(data['cardIDs'],dtype=torch.long))
        card_nums=torch.tensor(data['card_nums'],dtype=torch.float32)
        if(card_nums.shape[1]==0):
            card_nums=torch.reshape(card_nums,[*card_nums.shape,hparams['num_card_reals']])
        else:
            card_nums=self.norm_cards(card_nums)
        card_all=torch.cat([card_embed,card_nums],dim=2)
        action_embed=self.embed(torch.tensor(data['actionIDs'],dtype=torch.long))
        shape=action_embed.shape
        action_embed=action_embed.reshape(shape[0],shape[1],shape[2]*shape[3])
        return (reals,card_all,action_embed,data['action_mask'])

class ResidBlock(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams
        self.linone=nn.Linear(hparams["dot_dim"],hparams["dot_dim"])
        self.lintwo=nn.Linear(hparams["dot_dim"],hparams["dot_dim"])
    def forward(self,x):
        input=x
        x=F.relu(self.linone(x))
        x=F.relu(self.lintwo(x))
        x=x+input
        return x
class Trunk(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams
        act_dim=hparams["num_card_reals"]*hparams[ "embed_dim"]
        self.act_linear=nn.Linear(act_dim,hparams["dot_dim"])
        card_dim=hparams[ "embed_dim"]+hparams["num_card_reals"]
        self.cards_linear=nn.Linear(card_dim,hparams["dot_dim"])
        self.cards_resid=ResidBlock(hparams)
        self.reals_linear=nn.Linear(hparams["num_reals"],hparams["dot_dim"])
        self.reals_block=ResidBlock(hparams)
    def forward(self,reals,cards,action_embed):
        reals=self.reals_linear(reals)
        reals=self.reals_block(reals)
        if(cards.shape[1]>0):
            reals=torch.unsqueeze(reals,dim=1)
            cards=self.cards_linear(cards)
            cards=cards+reals
            cards=self.cards_resid(cards)
            cards=torch.mean(cards,dim=1)
            board_state=cards
        else:
            board_state=reals
        actions=self.act_linear(action_embed)
        return (board_state,actions)

class LinNet(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams
        act_dim=hparams["num_card_reals"]*hparams[ "embed_dim"]
        self.act_linear=nn.Linear(act_dim,hparams["dot_dim"])
        card_dim=hparams["embed_dim"]+hparams["num_card_reals"]
        self.cards_linear=nn.Linear(card_dim,hparams["dot_dim"])
        self.reals_linear=nn.Linear(hparams["num_reals"],hparams["dot_dim"])
    def forward(self,board_state,actions,action_mask):
        board_state=torch.unsqueeze(board_state,dim=-1)
        #print("boar state shape",board_state.shape)
        #print("actions shape",actions.shape)
        pred_values=actions@board_state
        pred_values=torch.squeeze(pred_values,dim=2)
        subtractor=1000*(1-action_mask)
        subtractor=torch.tensor(subtractor)
        pred_values=pred_values-subtractor
        pred_values=F.log_softmax(pred_values,dim=1)
        return pred_values

class ValueNet(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams
        self.act_lin=nn.Linear(hparams["dot_dim"],hparams["dot_dim"])
        self.block=ResidBlock(hparams)
        self.out_lin=nn.Linear(hparams["dot_dim"],1)
    def forward(self,board_state,actions):
        actions=torch.mean(actions,dim=1)
        actions=self.act_lin(actions)
        board_state=board_state+actions
        board_state=self.block(board_state)
        last_one=self.out_lin(board_state)
        return torch.tanh(last_one)

class MainNet(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams  
        self.prep=Preparer(hparams)
        self.trunk=Trunk(hparams)
        self.net=LinNet(hparams)   
        self.value_net=ValueNet(hparams)   
    def forward(self, converted):
        (reals,card_all,action_embed,action_mask)=self.prep(converted)
        (board_state,actions_trunk)=self.trunk(reals,card_all,action_embed) 
        actions=self.net(board_state,actions_trunk,action_mask) 
        values=self.value_net(board_state,actions_trunk) 
        return (actions,values)
def play_game(net,converter,env,verbose=False):
    observation = env.reset()
    actions=[]
    converted=converter.convert(observation)
    observations=[converted]
    rewards=[]
    while(True):    
        # your agent here (this takes random actions)
        with torch.no_grad(): 
            log_actions=net([converted])[0]
        log_actions=torch.squeeze(log_actions).detach()
        #print(log_actions.shape)
        if(verbose):
            print(torch.exp(log_actions))
        sample=torch.multinomial(torch.exp(log_actions),num_samples=1)
        #print("sample is",sample)
        action =int(sample[0])
        #print("action is",action)
        actions.append(action)
        observation, reward, done, info = env.step(action)
        rewards.append(reward)
        converted=converter.convert(observation,verbose=verbose)
        if(len(converted['actionIDs'])>0):
            observations.append(converted)
        else:
            break
    for i in reversed(range(len(rewards)-1)):
        rewards[i]=rewards[i+1]*hparams['decay']
    return actions,observations,rewards
