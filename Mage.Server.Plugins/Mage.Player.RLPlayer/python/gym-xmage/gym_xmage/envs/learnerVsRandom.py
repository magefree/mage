import gym
from gym import error, spaces, utils
from gym.utils import seeding
import socket
import json
import random
import numpy as np 
import pickle
class learnerVsRandom(gym.Env):
    metadata = {'render.modes': ['human']}

    def __init__(self):
        
        serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        serversocket.bind(("localhost",5000))
        print("waiting for connection")
        serversocket.listen(1)
        (self.clientsocket, address) = serversocket.accept()
        print("connected")
        self.java_hparams=self.recieve_and_parse(self.clientsocket)
        print("java hparams are",self.java_hparams)
        max_act=self.java_hparams['max_representable_actions']
        self.dumped=False
        self.action_space=spaces.Discrete(self.java_hparams['max_representable_actions'])
        self.observation_space=spaces.Box(0,self.java_hparams['max_represents'],shape=(69,),dtype=np.float32)
    def step(self, action):
        self.clientsocket.send(bytes(str(action),'ascii')+b"\n")
        message=self.recieve_and_parse(self.clientsocket)
        return (self.message_to_state(message),message['reward'],message['isDone'],{})
    def reset(self):
        message=self.recieve_and_parse(self.clientsocket)
        #done=self.recieve_msg(self.clientsocket)
        return self.message_to_state(message)
    def message_to_state(self,message):
        actions=message['actionRepr']
        state=message['gameRepr']
        actions=np.array(actions)
        #print('actions shape is',actions.shape)
        gameReals=np.array(state[0])
        #print('gameReals shape is',gameReals.shape)
        perms=np.array(state[1])
        #print('perms shape is',perms.shape)
        flat_features=(actions[0,:,0],actions[0,:,1],perms,gameReals)
        flat_lens=[len(item) for item in flat_features]
        if(not self.dumped):
            with open('featureShapes',"wb") as f:
                pickle.dump(flat_lens,f)
            self.dumped=True
        gamerepr=np.concatenate(flat_features,axis=0)
        return gamerepr
    def render(self, mode='human'):
        pass
    def close(self):
        pass
    def read_bytes(self,socket,num_bytes):
        read=b""
        while(len(read)<num_bytes):
            read+=socket.recv(num_bytes-len(read))
        return read
    def recieve_msg(self,socket):
        recvlen=self.read_bytes(socket,8)
        messagelen=int.from_bytes(recvlen,byteorder='big')
        message=self.read_bytes(socket,messagelen)
        return message
    def recieve_and_parse(self,socket):
        message=self.recieve_msg(socket)
        parsed=json.loads(message)
        #print(json.dumps(parsed, indent = 1))
        return parsed
