package mage.game.tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mage.cards.decks.Deck;
import mage.game.draft.DraftCube;

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
    protected boolean isRemixed;
    protected boolean isRichMan;
    protected Deck cubeFromDeck;

    protected String jumpstartPacks;
    // Eg of jumpstartPacks - uploaded by user
    /*
        # Minions
        1 JMP 236 Ghoulcaller Gisa
        1 JMP 226 Dutiful Attendant
        1 M21 126 Village Rites

        # Phyrexian
        1 JMP 278 Sheoldred, Whispering One
        1 JMP 227 Entomber Exarch
        1 JMP 265 Phyrexian Gargantua
    */
    protected boolean isJumpstart;

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

    public void setJumpstartPacks(String jumpstartPacks) {
        this.jumpstartPacks = jumpstartPacks;
    }

    public String getJumpstartPacks() {
        return jumpstartPacks;
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

    public boolean getIsRemixed() {
        return isRemixed;
    }

    public void setIsRemixed(boolean isRemixed) {
        this.isRemixed = isRemixed;
    }

    public boolean getIsRichMan() {
        return isRichMan;
    }

    public void setIsRichMan(boolean isRichMan) {
        this.isRichMan = isRichMan;
    }

    public void setIsJumpstart(boolean isJumpstart) {
        this.isJumpstart = isJumpstart;
    }

    public boolean getIsJumpstart() {
        return this.isJumpstart;
    }

}
