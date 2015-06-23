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

package mage.game.draft;

import java.util.ArrayList;
import java.util.List;
import mage.cards.ExpansionSet;
import java.util.Collections;
import java.lang.RuntimeException;

/**
 *
 * @author BrodyLodmell_at_googlemail.com
 */
public class ChaosBoosterDraft extends BoosterDraft {

    List<ExpansionSet> allSets;
    List<ExpansionSet> usedBoosters;
    public ChaosBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
        if (sets.isEmpty()){
            throw new RuntimeException("At least one set must be selected for chaos booster draft");
        }
        allSets = new ArrayList<>(sets);
        resetBoosters();
    }

    @Override
    public void start() {
        while (!isAbort() && boosterNum < numberBoosters) {
            openBooster();
            while (!isAbort() && pickCards()) {
                if (boosterNum % 2 == 1) {
                    passLeft();
                } else {
                    passRight();
                }
                fireUpdatePlayersEvent();
            }
        }
        resetBufferedCards();
        this.fireEndDraftEvent();
    }

    @Override
    protected void openBooster() {
        if (boosterNum < numberBoosters) {
            for (DraftPlayer player: players.values()) {
                player.setBooster(getNextBooster().createBooster());
            }
        }
        boosterNum++;
        cardNum = 1;
        fireUpdatePlayersEvent();
    }

    private ExpansionSet getNextBooster() {
        if (0 == usedBoosters.size()){
            resetBoosters();
        }
        ExpansionSet theBooster = usedBoosters.get(0);
        usedBoosters.remove(theBooster);
        return theBooster;
    }

    private void resetBoosters(){
        usedBoosters = new ArrayList<>(allSets);
        Collections.shuffle(usedBoosters);
    }
}
