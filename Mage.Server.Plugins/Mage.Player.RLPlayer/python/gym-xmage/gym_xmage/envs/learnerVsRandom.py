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
        serversocket.bind(("localhost",5009))
        print("waiting for connection")
        serversocket.listen(1)
        (self.clientsocket, address) = serversocket.accept()
        print("connected")
    def write_args(self):
        args={}
        args['deck1']="/home/elchanan/java/mage/Mage.Tests/RBTestAggro.dck"
        args['deck2']="/home/elchanan/java/mage/Mage.Tests/RBTestAggro.dck"
        args['player1']="player1"
        args['player2']="player2"
        args['player2random']="true"
        arg_string=json.dumps(args)
        self.clientsocket.send(bytes(arg_string,'ascii')+b"\n")

    def step(self, action):
        self.clientsocket.send(bytes(str(action),'ascii')+b"\n")
        message=self.recieve_and_parse(self.clientsocket)
        return (message,message["winReward"],message['isDone'],{})
    def sample_obs(self,observation):
        return random.randrange(len(observation['actions']))
    def reset(self):
        self.write_args()
        message=self.recieve_and_parse(self.clientsocket)
        #done=self.recieve_msg(self.clientsocket)
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
        #print(json.dumps(parsed, indent = 1))
        return parsed
