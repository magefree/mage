package mage.game.draft;

import mage.cards.ExpansionSet;

import java.util.List;

public class RemixedBoosterDraft extends BoosterDraft {

    final RemixedSet remixedSet;

    public RemixedBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
        if (sets.isEmpty()){
            throw new RuntimeException("At least one set must be selected for remixed booster draft");
        }
        remixedSet = new RemixedSet(sets, 10, 3, 1);
    }

    @Override
    protected void openBooster() {
        if (boosterNum <= numberBoosters) {
            for (DraftPlayer player: players.values()) {
                player.setBooster(remixedSet.createBooster());
            }
        }
    }

}
