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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public class SpitefulReturned extends CardImpl {

    public SpitefulReturned(UUID ownerId) {
        super(ownerId, 84, "Spiteful Returned", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Zombie");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {3}{B}
        this.addAbility(new BestowAbility(this, "{3}{B}"));
        // Whenever Spiteful Returned or enchanted creature attacks, defending player loses 2 life.
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("defending player loses 2 life");
        this.addAbility(new SpitefulReturnedTriggeredAbility(effect));
        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    public SpitefulReturned(final SpitefulReturned card) {
        super(card);
    }

    @Override
    public SpitefulReturned copy() {
        return new SpitefulReturned(this);
    }
}

class SpitefulReturnedTriggeredAbility extends TriggeredAbilityImpl {

    public SpitefulReturnedTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public SpitefulReturnedTriggeredAbility(final SpitefulReturnedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpitefulReturnedTriggeredAbility copy() {
        return new SpitefulReturnedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(this.getSourceId());
        if (sourcePermanent != null) {
            if (sourcePermanent.getCardType().contains(CardType.CREATURE)) {
                if (event.getSourceId() != null
                        && event.getSourceId().equals(this.getSourceId())) {
                    UUID defender = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
                    return true;
                }
            } else {
                if (sourcePermanent.getAttachedTo() != null && sourcePermanent.getAttachedTo().equals(event.getSourceId())) {
                    UUID defender = game.getCombat().getDefendingPlayerId(sourcePermanent.getAttachedTo(), game);
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever {this} or enchanted creature attacks, ").append(super.getRule()).toString();
    }
}
