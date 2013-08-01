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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.cards.CardImpl;

/**
 *
 * @author Backfir3
 */
public class Cockatrice extends CardImpl<Cockatrice> {

    public Cockatrice(UUID ownerId) {
        super(ownerId, 98, "Cockatrice", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "LEA";
        this.subtype.add("Cockatrice");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

		// Flying
		this.addAbility(FlyingAbility.getInstance());
        // Whenever Cockatrice blocks or becomes blocked by a non-Wall creature, destroy that creature at end of combat.
        this.addAbility(new CockatriceTriggeredAbility());
    }

    public Cockatrice(final Cockatrice card) {
        super(card);
    }

    @Override
    public Cockatrice copy() {
        return new Cockatrice(this);
    }
}

class CockatriceTriggeredAbility extends TriggeredAbilityImpl<CockatriceTriggeredAbility> {

    CockatriceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CockatriceEffect());
    }

    CockatriceTriggeredAbility(final CockatriceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CockatriceTriggeredAbility copy() {
        return new CockatriceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            Permanent blocked = game.getPermanent(event.getTargetId());
            Permanent cockatrice = game.getPermanent(sourceId);
            if (blocker != null && blocker != cockatrice
                    && !blocker.getSubtype().contains("Wall")
                    && blocked == cockatrice) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getId()));
                return true;
            }
            if (blocker != null && blocker == cockatrice
                    && !blocked.getSubtype().contains("Wall")) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(blocked.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks or becomes blocked by a non-Wall creature, destroy that creature at end of combat.";
    }
}

class CockatriceEffect extends OneShotEffect<CockatriceEffect> {

    CockatriceEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy that creature at end of combat";
    }

    CockatriceEffect(final CockatriceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect());
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(targetCreature.getId()));
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }

    @Override
    public CockatriceEffect copy() {
        return new CockatriceEffect(this);
    }
}
