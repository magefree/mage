import gym
import pickle 

from stable_baselines3 import PPO
from custom_pytorchmodel import CustomActorCriticPolicy
# multiprocess environment
# env = make_vec_env('CartPole-v1', n_envs=4)
env=gym.make('gym_xmage:learnerVsRandom-v0')
with open('hparams',"wb") as f:
    pickle.dump(env.java_hparams,f)

#model = PPO('MlpPolicy', env, verbose=1)
model = PPO(CustomActorCriticPolicy, env, verbose=1)
model.learn(total_timesteps=300000)
model.save("pythonv1")