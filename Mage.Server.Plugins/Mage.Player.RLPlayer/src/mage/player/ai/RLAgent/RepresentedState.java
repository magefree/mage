package mage.player.ai.RLAgent;
import java.util.*;

public class RepresentedState {
    public List<List<Integer>> actions;
    public List<List<Integer>> permanents;
    public List<Integer> gameInts;
    public Integer chosenAction;//Set it to null to ensure it is not used before it is assogned
    public Float reward;//Same with reward
    public Float rewardScale;
    public RepresentedState(List<List<Integer>> actions,List<List<Integer>> permanents,List<Integer> gameInts)
    {
        this.actions=actions;
        this.permanents=permanents;
        this.gameInts=gameInts;
    }
}
