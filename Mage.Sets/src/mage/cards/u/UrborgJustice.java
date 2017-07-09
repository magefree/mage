/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.u;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author andyfries
 */
public class UrborgJustice extends CardImpl {

    public UrborgJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        // Target opponent sacrifices a creature for each creature put into your graveyard from the battlefield this turn.
        this.getSpellAbility().addWatcher(new CreaturesDiedWatcher());
        SacrificeEffect sacrificeEffect = new SacrificeEffect(new FilterCreaturePermanent(), new UrborgJusticeDynamicValue(), "");
        sacrificeEffect.setText("Target opponent sacrifices a creature for each creature put into your graveyard from the battlefield this turn");

        this.getSpellAbility().addEffect(sacrificeEffect);
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public UrborgJustice(final UrborgJustice card) {
        super(card);
    }

    @Override
    public UrborgJustice copy() {
        return new UrborgJustice(this);
    }
}

class UrborgJusticeDynamicValue implements DynamicValue {

    @Override
    public UrborgJusticeDynamicValue copy() {
        return new UrborgJusticeDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "creature put into your graveyard from the battlefield this turn";
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CreaturesDiedWatcher watcher = (CreaturesDiedWatcher) game.getState().getWatchers().get(CreaturesDiedWatcher.class.getSimpleName());
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurnByOwner(sourceAbility.getControllerId());
        }
        return 0;
    }
}
