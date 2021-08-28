

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mage.cards.ExpansionSet;
import mage.game.draft.Draft;
import mage.game.draft.DraftCube;
import mage.game.draft.DraftPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<String> sets = new ArrayList<>();
    private final List<String> setCodes = new ArrayList<>();
    private final int boosterNum; // starts with 1
    private final int cardNum; // starts with 1
    private final List<String> players = new ArrayList<>();

    public DraftView(Draft draft) {
        if (draft.getDraftCube() != null) {
            for (int i = 0; i < draft.getNumberBoosters(); i++) {
                DraftCube cube = draft.getDraftCube();
                sets.add(cube.getName());
                setCodes.add(cube.getCode());
            }
        } else {
            for (ExpansionSet set: draft.getSets()) {
                sets.add(set.getName());
                setCodes.add(set.getCode());
            }
        }
        this.boosterNum = draft.getBoosterNum();
        this.cardNum = draft.getCardNum();
        for(DraftPlayer draftPlayer :draft.getPlayers()) {
            players.add(draftPlayer.getPlayer().getName());
        }
    }

    public List<String> getSets() {
        return sets;
    }

    public List<String> getSetCodes() {
        return setCodes;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getBoosterNum() {
        return boosterNum;
    }

    public int getCardNum() {
        return cardNum;
    }
}
