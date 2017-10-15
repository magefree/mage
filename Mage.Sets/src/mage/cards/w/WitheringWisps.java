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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author L_J
 */
public class WitheringWisps extends CardImpl {

    private static final String ruleText = "At the beginning of the end step, if no creatures are on the battlefield, sacrifice {this}.";

    public WitheringWisps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // At the beginning of the end step, if no creatures are on the battlefield, sacrifice Withering Wisps.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect());
        this.addAbility(new ConditionalTriggeredAbility(triggered, new CreatureCountCondition(0, TargetController.ANY), ruleText));

        // {B}: Withering Wisps deals 1 damage to each creature and each player. Activate this ability no more times each turn than the number of snow Swamps you control.
        this.addAbility(new WitheringWispsActivatedAbility());
    }

    public WitheringWisps(final WitheringWisps card) {
        super(card);
    }

    @Override
    public WitheringWisps copy() {
        return new WitheringWisps(this);
    }
}

class WitheringWispsActivatedAbility extends ActivatedAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent("snow Swamps you control");

    static {
        filter.add(new SupertypePredicate(SuperType.SNOW));
        filter.add(new SubtypePredicate(SubType.SWAMP));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    @Override
    public int getMaxActivationsPerTurn(Game game) {
        return game.getBattlefield().getAllActivePermanents(filter, game).size();
    }

    public WitheringWispsActivatedAbility() {
        super(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl("{B}"));

    }

    public WitheringWispsActivatedAbility(final WitheringWispsActivatedAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability no more times each turn than the number of snow Swamps you control.";
    }

    @Override
    public WitheringWispsActivatedAbility copy() {
        return new WitheringWispsActivatedAbility(this);
    }
}
