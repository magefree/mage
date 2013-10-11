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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PardicDragon extends CardImpl<PardicDragon> {

    public PardicDragon(UUID ownerId) {
        super(ownerId, 173, "Pardic Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {R}: Pardic Dragon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
        // Suspend 2-{R}{R}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl("{R}{R}"), this, true));
        // Whenever an opponent casts a spell, if Pardic Dragon is suspended, that player may put a time counter on Pardic Dragon.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(Zone.EXILED, new PardicDragonEffect(), new FilterSpell(), false, true),
                SuspendedCondition.getInstance(),
                "Whenever an opponent casts a spell, if {this} is suspended, that player may put a time counter on Pardic Dragon."
                ));

    }

    public PardicDragon(final PardicDragon card) {
        super(card);
    }

    @Override
    public PardicDragon copy() {
        return new PardicDragon(this);
    }
}

class PardicDragonEffect extends OneShotEffect<PardicDragonEffect> {

    public PardicDragonEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player may put a time counter on Pardic Dragon";
    }

    public PardicDragonEffect(final PardicDragonEffect effect) {
        super(effect);
    }

    @Override
    public PardicDragonEffect copy() {
        return new PardicDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Card sourceCard = game.getCard(source.getSourceId());
        if (opponent != null && sourceCard != null) {
            if (opponent.chooseUse(outcome, new StringBuilder("Put a time counter on ").append(sourceCard.getName()).append("?").toString(), game)) {
                sourceCard.addCounters(CounterType.TIME.createInstance(), game);
            }
            return true;
        }
        return false;
    }
}
