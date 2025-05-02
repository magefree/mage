package mage.game.draft;

import mage.cards.ExpansionSet;

import java.util.List;

public class ReshuffledBoosterDraft extends BoosterDraft {

    final ReshuffledSet reshuffledSet;

    public ReshuffledBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
        if (sets.isEmpty()){
            throw new IllegalStateException("At least one set must be selected for reshuffled booster draft");
        }
        reshuffledSet = new ReshuffledSet(sets, 10, 3, 1);
    }

    @Override
    protected void openBooster() {
        if (boosterNum <= numberBoosters) {
            for (DraftPlayer player: players.values()) {
                player.setBoosterAndLoad(reshuffledSet.createBooster());
            }
        }
    }

}
