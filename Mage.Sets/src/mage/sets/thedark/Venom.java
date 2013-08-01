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
package mage.sets.thedark;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Backfir3
 */
public class Venom extends CardImpl<Venom> {

    public Venom(UUID ownerId) {
        super(ownerId, 53, "Venom", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");
        this.expansionSetCode = "DRK";
        this.subtype.add("Aura");
        this.color.setGreen(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature blocks or becomes blocked by a non-Wall creature, destroy the other creature at end of combat.
        this.addAbility(new VenomTriggeredAbility());
    }

    public Venom(final Venom card) {
        super(card);
    }

    @Override
    public Venom copy() {
        return new Venom(this);
    }
}

class VenomTriggeredAbility extends TriggeredAbilityImpl<VenomTriggeredAbility> {

    VenomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VenomEffect());
    }

    VenomTriggeredAbility(final VenomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VenomTriggeredAbility copy() {
        return new VenomTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            Permanent blocked = game.getPermanent(event.getTargetId());
			Permanent enchantment = game.getPermanent(this.getSourceId());
			if (enchantment != null && enchantment.getAttachedTo() != null) {
				Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
				if (enchantedCreature != null) {
					if (blocker != null && blocker != enchantedCreature
						&& !blocker.getSubtype().contains("Wall")
						&& blocked == enchantedCreature) {
                            this.getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getId()));
							return true;
					}
					if (blocker != null && blocker == enchantedCreature
						&& !blocked.getSubtype().contains("Wall")) {
                        this.getEffects().get(0).setTargetPointer(new FixedTarget(blocked.getId()));
						return true;
					}
				}
			}
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature blocks or becomes blocked by a non-Wall creature, destroy that creature at end of combat.";
    }
}

class VenomEffect extends OneShotEffect<VenomEffect> {

    VenomEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy that creature at end of combat";
    }

    VenomEffect(final VenomEffect effect) {
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
    public VenomEffect copy() {
        return new VenomEffect(this);
    }
}
