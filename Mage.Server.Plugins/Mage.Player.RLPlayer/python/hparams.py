hparams={
    "max_represent":256,
    "embed_dim":12,
    "num_card_reals":2,
    "num_action_embeds":2,
    "dot_dim":10,
    "num_reals":7,
    "lr":.001,
    "decay":1.0, #decay of reward, set to 1 for now because it lets value be better predicted
    "entropy_weight":.0005,
    "batch_cutoff":10,
    "decay_steps":2000,
}