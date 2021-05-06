import gym
env=gym.make('gym_xmage:learnerVsRandom-v0')

class Representer():
    def __init__(self):
        names_to_ints=dict()
    #Return list of IDs, list of action IDs 
    def convert(obs):
        obs=obs['game']
        reals=obs['reals']
        perms=obs['permanents']
        print(reals)
converter=Representer() 
for games in range(5):
    observation = env.reset()
    while(True):
        env.render()
        # your agent here (this takes random actions)
        action = env.sample_obs(observation)
        observation, reward, done, info = env.step(action)
        converter.convert(observation)
        if done:
            env.render()
            break