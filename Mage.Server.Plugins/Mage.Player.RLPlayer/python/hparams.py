hparams={
    "max_represent":256,
    "embed_dim":12,
    "num_card_reals":2,
    "num_action_embeds":2,
    "dot_dim":32,
    "num_reals":7,
    "lr":.005,
    #"decay":.99, #decay of reward, set to 1 for now because it lets value be better predicted
    "entropy_weight":.000,
    "batch_cutoff":10,
    "decay_steps":10000,
    "grad_clip":10,
    "critic_weight":.01,
    "PPOeps":.2,
    'steps_per_batch':5,
    'exp_per_batch':2000,
}