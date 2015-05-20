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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class LifebloodHydra extends CardImpl {

    public LifebloodHydra(UUID ownerId) {
        super(ownerId, 45, "Lifeblood Hydra", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{X}{G}{G}{G}");
        this.expansionSetCode = "C14";
        this.subtype.add("Hydra");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Lifeblood Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new LifebloodHydraComesIntoPlayEffect(), "with X +1/+1 counters on it"));

        // When Lifeblood Hydra dies, you gain life and draw cards equal to its power.
        this.addAbility(new DiesTriggeredAbility(new LifebloodHydraEffect(), false));
    }

    public LifebloodHydra(final LifebloodHydra card) {
        super(card);
    }

    @Override
    public LifebloodHydra copy() {
        return new LifebloodHydra(this);
    }
}

class LifebloodHydraComesIntoPlayEffect extends OneShotEffect {

    public LifebloodHydraComesIntoPlayEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with X +1/+1 counters on it";
    }

    public LifebloodHydraComesIntoPlayEffect(final LifebloodHydraComesIntoPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && !permanent.isFaceDown(game)) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility) {
                int amount = ((SpellAbility) obj).getManaCostsToPay().getX();
                if (amount > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
                }
            }
        }
        return true;
    }

    @Override
    public LifebloodHydraComesIntoPlayEffect copy() {
        return new LifebloodHydraComesIntoPlayEffect(this);
    }

}
class LifebloodHydraEffect extends OneShotEffect {

    public LifebloodHydraEffect() {
        super(Outcome.Benefit);
        this.staticText = "you gain life and draw cards equal to its power";
    }

    public LifebloodHydraEffect(final LifebloodHydraEffect effect) {
        super(effect);
    }

    @Override
    public LifebloodHydraEffect copy() {

        return new LifebloodHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent diedPermanent = (Permanent) getValue("diedPermanent");
            if (diedPermanent != null) {
                controller.gainLife(diedPermanent.getPower().getValue(), game);
                controller.drawCards(diedPermanent.getPower().getValue(), game);
            }
            return true;
        }
        return false;
    }
}
