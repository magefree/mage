package mage.player.ai.RLAgent;

public class RepresentedState {
    int[][] actions;
    int[][] permanents;
    int[][] permanentInts;
    int[] gameInts;
    public RepresentedState(int[][] actions,int[][] permanents,int[][] permanentInts,int[] gameInts)
    {
        this.actions=actions;
        this.permanents=permanents;
        this.permanentInts=permanentInts;
        this.gameInts=gameInts;
    }
}
