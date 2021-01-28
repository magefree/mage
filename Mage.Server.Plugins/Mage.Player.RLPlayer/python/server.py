import gym
import pickle 

from stable_baselines3 import PPO
from custom_pytorchmodel import CustomActorCriticPolicy
# multiprocess environment
# env = make_vec_env('CartPole-v1', n_envs=4)
env=gym.make('gym_xmage:learnerVsRandom-v0')

model = PPO.load("pythonv1")

obs = env.reset()
while True:
    action, _states = model.predict(obs)
    obs, rewards, dones, info = env.step(action)
    #env.render()