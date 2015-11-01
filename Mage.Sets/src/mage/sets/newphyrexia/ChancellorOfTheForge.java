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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.GoblinTokenWithHaste;

/**
 *
 * @author BetaSteward
 */
public class ChancellorOfTheForge extends CardImpl {

    private static String abilityText = "at the beginning of the first upkeep, put a 1/1 red Goblin creature token with haste onto the battlefield";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public ChancellorOfTheForge(UUID ownerId) {
        super(ownerId, 81, "Chancellor of the Forge", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Giant");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, put a 1/1 red Goblin creature token with haste onto the battlefield.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheForgeDelayedTriggeredAbility(), abilityText));

        // When Chancellor of the Forge enters the battlefield, put X 1/1 red Goblin creature tokens with haste onto the battlefield, where X is the number of creatures you control.
        DynamicValue value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinTokenWithHaste(), value), false));
    }

    public ChancellorOfTheForge(final ChancellorOfTheForge card) {
        super(card);
    }

    @Override
    public ChancellorOfTheForge copy() {
        return new ChancellorOfTheForge(this);
    }
}

class ChancellorOfTheForgeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheForgeDelayedTriggeredAbility () {
        super(new CreateTokenEffect(new GoblinTokenWithHaste()));
    }

    ChancellorOfTheForgeDelayedTriggeredAbility(ChancellorOfTheForgeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
    @Override
    public ChancellorOfTheForgeDelayedTriggeredAbility copy() {
        return new ChancellorOfTheForgeDelayedTriggeredAbility(this);
    }
}
