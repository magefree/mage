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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.permanent.token.Token;
import mage.watchers.common.NonCombatDamageWatcher;

/**
 *
 * @author Styxo
 */
public class IronFistOfTheEmpire extends CardImpl {

    public IronFistOfTheEmpire(UUID ownerId) {
        super(ownerId, 202, "Iron Fist of the Empire", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");
        this.expansionSetCode = "SWS";

        // <i>Hate</i> &mdash; At the beggining of each end step, if opponent lost life from a source other than combat damage this turn, you gain 1 life and create a 2/2 red Soldier creature token with first strike name Royal Guard.
        TriggeredAbility triggeredAbility = new BeginningOfEndStepTriggeredAbility(new GainLifeEffect(1), TargetController.ANY, false);
        triggeredAbility.addEffect(new CreateTokenEffect(new RoyalGuardToken()));
        Ability ability = new ConditionalTriggeredAbility(
                triggeredAbility,
                HateCondition.getInstance(),
                "<i>Hate</i> &mdash; At the beggining of each end step, if opponent lost life from a source other than combat damage this turn, you gain 1 life and create a 2/2 red Soldier creature token with first strike named Royal Guard.");
        this.addAbility(ability, new NonCombatDamageWatcher());
    }

    public IronFistOfTheEmpire(final IronFistOfTheEmpire card) {
        super(card);
    }

    @Override
    public IronFistOfTheEmpire copy() {
        return new IronFistOfTheEmpire(this);
    }
}

class RoyalGuardToken extends Token {

    public RoyalGuardToken() {
        super("Royal Guard", "2/2 red Soldier creature token with first strike named Royal Guard", 2, 2);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        addAbility(FirstStrikeAbility.getInstance());
        subtype.add("Soldier");
    }
}
