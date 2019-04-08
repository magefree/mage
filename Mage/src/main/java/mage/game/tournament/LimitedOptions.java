package mage.game.tournament;

import mage.cards.decks.Deck;
import mage.game.draft.DraftCube;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LimitedOptions implements Serializable {

    protected List<String> sets = new ArrayList<>();
    protected int constructionTime;
    protected String draftCubeName;
    protected DraftCube draftCube;
    protected int numberBoosters;
    protected boolean isRandom;
    protected boolean isRichMan;
    protected Deck cubeFromDeck = null;

    public List<String> getSetCodes() {
        return sets;
    }

    public int getConstructionTime() {
        return constructionTime;
    }

    public void setConstructionTime(int constructionTime) {
        this.constructionTime = constructionTime;
    }

    public String getDraftCubeName() {
        return draftCubeName;
    }

    public void setDraftCubeName(String draftCubeName) {
        this.draftCubeName = draftCubeName;
    }

    public void setCubeFromDeck(Deck cubeFromDeck) {
        this.cubeFromDeck = cubeFromDeck;
    }

    public Deck getCubeFromDeck() {
        return cubeFromDeck;
    }

    public DraftCube getDraftCube() {
        return draftCube;
    }

    public void setDraftCube(DraftCube draftCube) {
        this.draftCube = draftCube;
    }

    public int getNumberBoosters() {
        return numberBoosters;
    }

    public void setNumberBoosters(int numberBoosters) {
        this.numberBoosters = numberBoosters;
    }

    public boolean getIsRandom() {
        return isRandom;
    }

    public void setIsRandom(boolean isRandom) {
        this.isRandom = isRandom;
    }

    public boolean getIsRichMan() {
        return isRichMan;
    }

    public void setIsRichMan(boolean isRichMan) {
        this.isRichMan = isRichMan;
    }
}
