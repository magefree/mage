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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class KreshTheBloodbraided extends CardImpl<KreshTheBloodbraided> {

    public KreshTheBloodbraided(UUID ownerId) {
        super(ownerId, 178, "Kresh the Bloodbraided", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}");
        this.expansionSetCode = "ALA";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever another creature dies, you may put X +1/+1 counters on Kresh the Bloodbraided, where X is that creature's power.
        this.addAbility(new DiesCreatureTriggeredAbility(new KreshTheBloodbraidedEffect(), true, true));
    }

    public KreshTheBloodbraided(final KreshTheBloodbraided card) {
        super(card);
    }

    @Override
    public KreshTheBloodbraided copy() {
        return new KreshTheBloodbraided(this);
    }
}

class KreshTheBloodbraidedEffect extends OneShotEffect<KreshTheBloodbraidedEffect> {

    KreshTheBloodbraidedEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "you may put X +1/+1 counters on Kresh the Bloodbraided, where X is that creature's power";
    }

    KreshTheBloodbraidedEffect(final KreshTheBloodbraidedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creature = game.getCard(targetPointer.getFirst(game, source));
        Permanent KreshTheBloodbraided = game.getPermanent(source.getSourceId());

        if (creature != null && KreshTheBloodbraided != null) {
            KreshTheBloodbraided.addCounters(CounterType.P1P1.createInstance(creature.getPower().getValue()), game);
            return true;
        }
        return false;
    }

    @Override
    public KreshTheBloodbraidedEffect copy() {
        return new KreshTheBloodbraidedEffect(this);
    }
}