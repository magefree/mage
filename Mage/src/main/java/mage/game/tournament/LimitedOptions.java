/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.game.tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mage.cards.decks.Deck;
import mage.game.draft.DraftCube;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LimitedOptions implements Serializable {

    protected List<String> sets = new ArrayList<>();
    protected int constructionTime;
    protected String draftCubeName;
    protected DraftCube draftCube;
    protected int numberBoosters;
    protected boolean isRandom;
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

    public boolean getIsRandom(){
        return isRandom;
    }
    public void setIsRandom(boolean isRandom){
        this.isRandom = isRandom;
    }

}
