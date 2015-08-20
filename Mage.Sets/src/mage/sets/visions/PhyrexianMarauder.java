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
package mage.sets.visions;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessPaysSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class PhyrexianMarauder extends CardImpl {

    public PhyrexianMarauder(UUID ownerId) {
        super(ownerId, 151, "Phyrexian Marauder", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}");
        this.expansionSetCode = "VIS";
        this.subtype.add("Construct");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Phyrexian Marauder enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new PhyrexianMarauderEntersEffect(), "with X +1/+1 counters on it"));

        // Phyrexian Marauder can't block.
        this.addAbility(new CantBlockAbility());

        // Phyrexian Marauder can't attack unless you pay {1} for each +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianMarauderCantAttackUnlessYouPayEffect()));
    }

    public PhyrexianMarauder(final PhyrexianMarauder card) {
        super(card);
    }

    @Override
    public PhyrexianMarauder copy() {
        return new PhyrexianMarauder(this);
    }
}

class PhyrexianMarauderEntersEffect extends OneShotEffect {

    PhyrexianMarauderEntersEffect() {
        super(Outcome.BoostCreature);
    }

    PhyrexianMarauderEntersEffect(final PhyrexianMarauderEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && !permanent.isFaceDown(game)) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility
                    && permanent.getZoneChangeCounter(game) - 1 == ((SpellAbility) obj).getSourceObjectZoneChangeCounter()) {
                int amount = ((Ability) obj).getManaCostsToPay().getX();
                if (amount > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
                }
            }
        }
        return true;
    }

    @Override
    public PhyrexianMarauderEntersEffect copy() {
        return new PhyrexianMarauderEntersEffect(this);
    }
}

class PhyrexianMarauderCantAttackUnlessYouPayEffect extends CantAttackBlockUnlessPaysSourceEffect {

    PhyrexianMarauderCantAttackUnlessYouPayEffect() {
        super(new ManaCostsImpl("{0}"), RestrictType.ATTACK);
        staticText = "{this} can't attack unless you pay {1} for each +1/+1 counter on it";
    }

    PhyrexianMarauderCantAttackUnlessYouPayEffect(PhyrexianMarauderCantAttackUnlessYouPayEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            int counter = sourceObject.getCounters().getCount(CounterType.P1P1);
            if (counter > 0) {
                return new ManaCostsImpl<>("{" + counter + "}");
            }
        }
        return null;
    }

    @Override
    public PhyrexianMarauderCantAttackUnlessYouPayEffect copy() {
        return new PhyrexianMarauderCantAttackUnlessYouPayEffect(this);
    }

}
