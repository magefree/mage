

package mage.game.draft;

import java.util.ArrayList;
import java.util.List;
import mage.cards.ExpansionSet;
import java.util.Collections;

/**
 *
 * @author BrodyLodmell_at_googlemail.com
 */
public class RandomBoosterDraft extends BoosterDraft {

    List<ExpansionSet> allSets;
    List<ExpansionSet> useBoosters;
    public RandomBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
        if (sets.isEmpty()){
            throw new RuntimeException("At least one set must be selected for random booster draft");
        }
        allSets = new ArrayList<>(sets);
        resetBoosters();
    }

    @Override
    protected void openBooster() {
        if (boosterNum <= numberBoosters) {
            for (DraftPlayer player: players.values()) {
                player.setBooster(getNextBooster().create15CardBooster());
            }
        }
    }

    private ExpansionSet getNextBooster() {
        if (useBoosters.isEmpty()){
            resetBoosters();
        }
        ExpansionSet theBooster = useBoosters.get(0);
        useBoosters.remove(theBooster);
        return theBooster;
    }

    private void resetBoosters(){
        useBoosters = new ArrayList<>(allSets);
        Collections.shuffle(useBoosters);
    }
}
