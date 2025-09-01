package mage.view;

import mage.cards.ExpansionSet;
import mage.game.draft.Draft;
import mage.game.draft.DraftCube;
import mage.game.draft.DraftPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class DraftView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<String> setNames = new ArrayList<>();
    private final List<String> setCodes = new ArrayList<>();
    private final int boosterNum; // starts with 1
    private final int cardNum; // starts with 1
    private final boolean isCube;

    private final List<String> players = new ArrayList<>();

    public DraftView(Draft draft) {
        this.isCube = draft.getDraftCube() != null;
        if (this.isCube) {
            for (int i = 0; i < draft.getNumberBoosters(); i++) {
                DraftCube cube = draft.getDraftCube();
                setNames.add(cube.getName());
                setCodes.add(cube.getCode());
            }
        } else {
            for (ExpansionSet set : draft.getSets()) {
                setNames.add(set.getName());
                setCodes.add(set.getCode());
            }
        }
        this.boosterNum = draft.getBoosterNum();
        this.cardNum = draft.getCardNum();
        for (DraftPlayer draftPlayer : draft.getPlayers()) {
            players.add(draftPlayer.getPlayer().getName());
        }
    }

    public String getBoosterInfo(int index) {
        if (index >= this.setCodes.size() || this.setCodes.size() != this.setNames.size()) {
            return "error";
        }

        if (this.isCube) {
            return this.setNames.get(index);
        } else {
            return String.join(" - ", this.setCodes.get(index), this.setNames.get(index));
        }
    }

    public List<String> getSetNames() {
        return setNames;
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
