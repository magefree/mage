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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class Thraximundar extends CardImpl<Thraximundar> {

    public Thraximundar(UUID ownerId) {
        super(ownerId, 113, "Thraximundar", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{U}{B}{R}");
        this.expansionSetCode = "ARB";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Assassin");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Thraximundar attacks, defending player sacrifices a creature.
        this.addAbility(new ThraximundarTriggeredAbility());

        // Whenever a player sacrifices a creature, you may put a +1/+1 counter on Thraximundar.
        this.addAbility(new PlayerSacrificesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true));

    }

    public Thraximundar(final Thraximundar card) {
        super(card);
    }

    @Override
    public Thraximundar copy() {
        return new Thraximundar(this);
    }
}

class ThraximundarTriggeredAbility extends TriggeredAbilityImpl<ThraximundarTriggeredAbility> {

    private static final FilterControlledPermanent filter;

    static {
        filter = new FilterControlledPermanent(" a creature");
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public ThraximundarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(filter, 1, "defending player"));
    }

    public ThraximundarTriggeredAbility(final ThraximundarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThraximundarTriggeredAbility copy() {
        return new ThraximundarTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                && event.getSourceId() == this.getSourceId()) {
            UUID defender = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, defending player sacrifices a creature.";
    }
}

class PlayerSacrificesCreatureTriggeredAbility extends TriggeredAbilityImpl<PlayerSacrificesCreatureTriggeredAbility> {

    public PlayerSacrificesCreatureTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public PlayerSacrificesCreatureTriggeredAbility(final PlayerSacrificesCreatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT) {
            MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (mageObject != null && mageObject.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player sacrifices a creature, " + super.getRule();
    }

    @Override
    public PlayerSacrificesCreatureTriggeredAbility copy() {
        return new PlayerSacrificesCreatureTriggeredAbility(this);
    }
}