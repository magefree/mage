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
package mage.sets.ravnika;

import java.util.UUID;

import mage.cards.CardImpl;
import mage.constants.Rarity;
import mage.constants.CardType;

import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.keyword.EnchantAbility;

import mage.abilities.TriggeredAbilityImpl;
import mage.constants.Zone;
import mage.game.events.GameEvent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.abilities.effects.OneShotEffect;
import mage.game.permanent.Permanent;
import mage.abilities.effects.Effect;
import mage.target.targetpointer.FixedTarget;
import mage.players.Player;
import mage.target.Target;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.turn.TurnMod;
import mage.constants.TurnPhase;
import mage.MageObject;
import mage.filter.predicate.Predicate;

/**
 * @author duncancmt
 */
public class BreathOfFury extends CardImpl {
    public BreathOfFury(UUID ownerId) {
        super(ownerId, 116, "Breath of Fury", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Aura");

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature deals combat damage to a player, sacrifice it and attach Breath of Fury to a creature you control. If you do, untap all creatures you control and after this phase, there is an additional combat phase.
        this.addAbility(new BreathOfFuryAbility());
    }

    public BreathOfFury(final BreathOfFury card) {
        super(card);
    }

    @Override
    public BreathOfFury copy() {
        return new BreathOfFury(this);
    }
}

class BreathOfFuryAbility extends TriggeredAbilityImpl {

    public BreathOfFuryAbility() {
        super(Zone.BATTLEFIELD, new BreathOfFuryEffect());
    }

    public BreathOfFuryAbility(final BreathOfFuryAbility ability) {
        super(ability);
    }

    @Override
    public BreathOfFuryAbility copy() {
        return new BreathOfFuryAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(p.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature deals combat damage to a player, sacrifice it and attach Breath of Fury to a creature you control. If you do, untap all creatures you control and after this phase, there is an additional combat phase.";
    }
}

class BreathOfFuryEffect extends OneShotEffect {

    public BreathOfFuryEffect() {
        super(Outcome.Benefit);
        staticText = "Sacrifice enchanted creature and attach Breath of Fury to a creature you control. If you do, untap all creatures you control and after this phase, there is an additional combat phase.";
    }

    public BreathOfFuryEffect(final BreathOfFuryEffect effect) {
        super(effect);
    }

    @Override
    public BreathOfFuryEffect copy() {
        return new BreathOfFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source){
        Permanent enchantedCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent enchantment = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        Target target = new TargetControlledCreaturePermanent(new FilterCanBeEnchantedControlledCreaturePermanent(enchantment));
        target.setNotTarget(true);
        if (enchantedCreature != null &&
            enchantedCreature.sacrifice(source.getSourceId(), game) &&
            enchantment != null &&
            controller != null &&
            target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
            controller.choose(outcome, target, source.getId(), game);
            Permanent newCreature = game.getPermanent(target.getFirstTarget());
            if (newCreature != null &&
                newCreature.addAttachment(enchantment.getId(), game)) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterControlledCreaturePermanent(),
                                                                                        controller.getId(), game)) {
                    permanent.untap(game);
                }
                game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
                return true;
            }
        }
        return false;
    }
}



class FilterCanBeEnchantedControlledCreaturePermanent extends FilterControlledCreaturePermanent {

    public FilterCanBeEnchantedControlledCreaturePermanent(final FilterCanBeEnchantedControlledCreaturePermanent filter) {
        super(filter);
    }

    public FilterCanBeEnchantedControlledCreaturePermanent(MageObject auraEnchantment) {
        super("creature you control that could be enchanted by " + auraEnchantment.getName());
        this.add(new CanBeEnchantedPredicate(auraEnchantment));
    }

    @Override
    public FilterCanBeEnchantedControlledCreaturePermanent copy() {
        return new FilterCanBeEnchantedControlledCreaturePermanent(this);
    }
}


class CanBeEnchantedPredicate implements Predicate<Permanent> {

    private final MageObject auraEnchantment;

    public CanBeEnchantedPredicate(MageObject auraEnchantment){
        this.auraEnchantment = auraEnchantment;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return !input.cantBeEnchantedBy(auraEnchantment, game);
    }

    @Override
    public String toString() {
        return "CanBeEnchanted(" + auraEnchantment.toString() + ")";
    }
}
