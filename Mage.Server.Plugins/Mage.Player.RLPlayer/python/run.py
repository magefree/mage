import gym
env=gym.make('gym_xmage:learnerVsRandom-v0')

observation = env.reset()
for _ in range(1000):
    env.render()
    # your agent here (this takes random actions)
    action = env.action_space.sample()
    observation, reward, done, info = env.step(action)
    if done:
        env.render()
    break
