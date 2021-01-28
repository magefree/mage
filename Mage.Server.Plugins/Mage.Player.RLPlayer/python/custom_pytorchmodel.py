from typing import Callable, Dict, List, Optional, Tuple, Type, Union

import gym
import torch as th
from torch import nn
import torch.nn.functional as F
import pickle 

from stable_baselines3 import PPO
from stable_baselines3.common.policies import ActorCriticPolicy
from stable_baselines3.common.torch_layers import BaseFeaturesExtractor
from stable_baselines3.common.preprocessing import get_flattened_obs_dim
from stable_baselines3.common.distributions import CategoricalDistribution

class IdentityCategorical(CategoricalDistribution):
    def __init__(self,latent_dim):
        super().__init__(latent_dim)

    def proba_distribution_net(self, latent_dim: int) -> nn.Module:
        """Categorical distribution, but just an identity module
        """
        assert latent_dim==self.action_dim
        return nn.Identity()
class MTGExtractor(BaseFeaturesExtractor):
    """
    Feature extract that flatten the input.
    :param observation_space:
    """

    def __init__(self, observation_space: gym.Space):
        super().__init__(observation_space, get_flattened_obs_dim(observation_space))
        self.space=observation_space
        self.flatten = nn.Flatten()

    def forward(self, observations):
        #print(observations)
        return observations
        #return self.flatten(observations)

class Block(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams
        self.blocka=nn.Conv1d(hparams['internal_dim'],hparams['internal_dim'],1)
        self.blockb=nn.Conv1d(hparams['internal_dim'],hparams['internal_dim'],1)
    def forward(self,data):
        start=data
        data=F.relu(data)
        data=self.blocka(data)
        data=F.relu(data)
        data=self.blockb(data)
        data=data+start
        return data

class Small_net(nn.Module):
    def __init__(self,hparams):
        super().__init__()
        self.hparams=hparams
        self.embed=nn.Embedding(hparams['max_represents'],hparams['internal_dim'])
        self.head=nn.Conv1d(hparams['internal_dim'],hparams['internal_dim'],1)
        self.block=Block(hparams)
        self.tail=nn.Conv1d(hparams['internal_dim'],1,1)
    def forward(self,data):
        parts=self.hparams['featureShapes']
        data=data[:,:sum([parts[i] for i in range(2)])]
        data=data.long()
        data=self.embed(data)
        actions=data[:,:parts[0],:]
        rest=data[:,parts[0]:,:]
        actions=actions#+.1*th.mean(rest,dim=1,keepdim=True)
        data=actions.permute(0,2,1)
        data=self.head(data)
        data=self.block(data)
        data=self.tail(data)
        data=data.reshape(-1,self.hparams['max_representable_actions'])
        #print(data.shape)
        return data
class CustomNetwork(nn.Module):
    """
    Custom network for policy and value function.
    It receives as input the features extracted by the feature extractor.

    :param feature_dim: dimension of the features extracted with the features_extractor (e.g. features from a CNN)
    :param last_layer_dim_pi: (int) number of units for the last layer of the policy network
    :param last_layer_dim_vf: (int) number of units for the last layer of the value network
    """

    def __init__(
        self,
        feature_dim: int,
        last_layer_dim_pi: int = 64,
        last_layer_dim_vf: int = 64,
    ):
        super(CustomNetwork, self).__init__()
        # IMPORTANT:
        # Save output dimensions, used to create the distributions
        with open('hparams',"rb") as f:
            hparams=pickle.load(f)
        try:
            with open('featureShapes',"rb") as f:
                hparams['featureShapes']=pickle.load(f)
        except:
            pass
        self.hparams=hparams
        self.latent_dim_pi = last_layer_dim_pi
        self.latent_dim_vf = hparams['max_representable_actions']

        self.feature_dim=feature_dim
        # Policy network
        self.policy_net = Small_net(hparams)
        self.value_net=Small_net(hparams)
        """nn.Sequential(
            nn.Linear(feature_dim, last_layer_dim_pi), nn.ReLU()
        )
        # Value network
        self.value_net = nn.Sequential(
            nn.Linear(feature_dim, last_layer_dim_vf), nn.ReLU()
        )"""

    def forward(self, features: th.Tensor) -> Tuple[th.Tensor, th.Tensor]:
        """
        :return: (th.Tensor, th.Tensor) latent_policy, latent_value of the specified network.
            If all layers are shared, then ``latent_policy == latent_value``
        """
        #print(features.shape)
        #print(features)
        return self.policy_net(features), self.value_net(features)


class CustomActorCriticPolicy(ActorCriticPolicy):
    def __init__(
        self,
        observation_space: gym.spaces.Space,
        action_space: gym.spaces.Space,
        lr_schedule: Callable[[float], float],
        net_arch: Optional[List[Union[int, Dict[str, List[int]]]]] = None,
        activation_fn: Type[nn.Module] = nn.Tanh,
        *args,
        **kwargs,
    ):

        super(CustomActorCriticPolicy, self).__init__(
            observation_space,
            action_space,
            lr_schedule,
            net_arch,
            activation_fn,
            features_extractor_class=MTGExtractor,
            # Pass remaining arguments to base class
            *args,
            **kwargs,
        )
        # Disable orthogonal initialization
        self.action_dist=IdentityCategorical(action_space.n)
        self.action_net = self.action_dist.proba_distribution_net(latent_dim=action_space.n)
        #self.ortho_init = False

    def _build_mlp_extractor(self) -> None:
        self.mlp_extractor = CustomNetwork(self.features_dim)
