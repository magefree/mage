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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continious.SetCardColorTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class Spiritmonger extends CardImpl<Spiritmonger> {

    public Spiritmonger(UUID ownerId) {
        super(ownerId, 121, "Spiritmonger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        this.expansionSetCode = "APC";
        this.subtype.add("Beast");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Spiritmonger deals damage to a creature, put a +1/+1 counter on Spiritmonger.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), false, false, false));
        // {B}: Regenerate Spiritmonger.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}")));
        // {G}: Spiritmonger becomes the color of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SpiritmongerChangeColorEffect(), new ManaCostsImpl("{G}")));
    }

    public Spiritmonger(final Spiritmonger card) {
        super(card);
    }

    @Override
    public Spiritmonger copy() {
        return new Spiritmonger(this);
    }
}

class SpiritmongerChangeColorEffect extends OneShotEffect<SpiritmongerChangeColorEffect> {

    public SpiritmongerChangeColorEffect() {
        super(Outcome.Neutral);
        staticText = "{this} becomes the color of your choice until end of turn";
    }

    public SpiritmongerChangeColorEffect(final SpiritmongerChangeColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent wildMongrel = game.getPermanent(source.getSourceId());
        if (player != null && wildMongrel != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Outcome.Neutral, colorChoice, game)) {
                game.informPlayers(wildMongrel.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                ContinuousEffect effect = new SetCardColorTargetEffect(colorChoice.getColor(), Duration.EndOfTurn, "is " + colorChoice.getChoice());
                effect.setTargetPointer(new FixedTarget(source.getSourceId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public SpiritmongerChangeColorEffect copy() {
        return new SpiritmongerChangeColorEffect(this);
    }
}
