package mage.player.ai.RLAgent;

import java.io.Serializable;


//Static members are not used so it can 
//be serialized for sending to Python
public class HParams implements Serializable {
    public HParams(){

    }
    //These are fixed numbered actions, counter 
    //start sould be 1 beyond these 
    public final int no_attack=1;
    public final int no_block=2;
    public final int yes_mulligan=3;
    public final int no_mulligan=4;
    public final int counter_start=5;
    public final int copy_time=5;
    public final int train_per_game=5; 
    public final double lr=.001;
    public final int batch_size=128;
    public final int max_represents=256; 
    public final int input_seqlen=2;
    public final int max_representable_actions=16;
    public final int max_representable_permanents=32; 
    public final int model_inputlen=max_representable_actions*input_seqlen;
    public final int internal_dim=64; //For speed for now, should be good enough
    public final int game_reals=5;
    public final int player_reals=2;
    public final int num_game_reprs=3;
    public final int total_model_inputs=1+num_game_reprs;
    public final double discount=.98;
    public final int games_to_keep=1000;
    public final boolean double_dqn=true; 
    public final int perm_features=4;
}
