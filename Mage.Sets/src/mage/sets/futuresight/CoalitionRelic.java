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
package mage.sets.futuresight;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfPreCombatMainDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaInAnyCombinationEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CoalitionRelic extends CardImpl<CoalitionRelic> {

    public CoalitionRelic(UUID ownerId) {
        super(ownerId, 161, "Coalition Relic", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "FUT";

        // {tap}: Add one mana of any color to your mana pool.
        this.addAbility(new AnyColorManaAbility());
        // {tap}: Put a charge counter on Coalition Relic.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true), new TapSourceCost()));
        // At the beginning of your precombat main phase, remove all charge counters from Coalition Relic. Add one mana of any color to your mana pool for each charge counter removed this way.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new CoalitionRelicEffect(), TargetController.YOU, false));
    }

    public CoalitionRelic(final CoalitionRelic card) {
        super(card);
    }

    @Override
    public CoalitionRelic copy() {
        return new CoalitionRelic(this);
    }
}

class CoalitionRelicEffect extends OneShotEffect<CoalitionRelicEffect> {

    public CoalitionRelicEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "remove all charge counters from Coalition Relic. Add one mana of any color to your mana pool for each charge counter removed this way";
    }

    public CoalitionRelicEffect(final CoalitionRelicEffect effect) {
        super(effect);
    }

    @Override
    public CoalitionRelicEffect copy() {
        return new CoalitionRelicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && player != null) {
            int chargeCounters = sourcePermanent.getCounters().getCount(CounterType.CHARGE);
            sourcePermanent.removeCounters(CounterType.CHARGE.createInstance(chargeCounters), game);
            Mana mana = new Mana();
            ChoiceColor choice = new ChoiceColor();
            for (int i = 0; i < chargeCounters; i++) {
                while (player.isInGame() && !choice.isChosen()) {
                    player.choose(outcome, choice, game);
                }
                if (choice.getColor().isBlack()) {
                    mana.addBlack();
                } else if (choice.getColor().isBlue()) {
                    mana.addBlue();
                } else if (choice.getColor().isRed()) {
                    mana.addRed();
                } else if (choice.getColor().isGreen()) {
                    mana.addGreen();
                } else if (choice.getColor().isWhite()) {
                    mana.addWhite();
                }
                choice.clearChoice();
            }
            player.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
