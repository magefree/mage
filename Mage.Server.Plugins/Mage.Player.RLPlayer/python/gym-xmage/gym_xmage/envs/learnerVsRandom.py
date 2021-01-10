import gym
from gym import error, spaces, utils
from gym.utils import seeding
import socket
import json
import random

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
        self.action_space=spaces.Discrete(self.java_hparams['max_representable_actions'])
    def step(self, action):
        clientsocket.send(bytes(str(action),'ascii')+b"\n")
        message=self.recieve_and_parse(self.clientsocket)
        done=self.recieve_msg(self.clientsocket)
        if(done=="not done"):
            return (message,0,False,"")
        else:
            reward=int(done)
            return (message,reward,True,"")
    def reset(self):
        done=self.recieve_msg(self.clientsocket)
        message=self.recieve_and_parse(self.clientsocket)
        return message
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
        return parsed
