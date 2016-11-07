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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class CapturedByTheConsulate extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public CapturedByTheConsulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");
        this.subtype.add("Aura");

        // Enchant creature you don't control
        TargetPermanent auraTarget = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAttachedEffect(AttachmentType.AURA)));

        // Whenever an opponent casts a spell, if it has a single target, change the target to enchanted creature if able.
        this.addAbility(new CapturedByTheConsulateTriggeredAbility(Zone.BATTLEFIELD, new CapturedByTheConsulateEffect()));

    }

    public CapturedByTheConsulate(final CapturedByTheConsulate card) {
        super(card);
    }

    @Override
    public CapturedByTheConsulate copy() {
        return new CapturedByTheConsulate(this);
    }
}

class CapturedByTheConsulateTriggeredAbility extends TriggeredAbilityImpl {

    /**
     *
     * @param zone
     * @param effect
     */
    public CapturedByTheConsulateTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
    }

    public CapturedByTheConsulateTriggeredAbility(final CapturedByTheConsulateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        StackObject stackObject = null;
        for (Effect effect : this.getEffects()) {
            stackObject = game.getStack().getStackObject(effect.getTargetPointer().getFirst(game, this));
        }
        if (stackObject != null) {
            int numberOfTargets = 0;
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    numberOfTargets += target.getTargets().size();
                }
            }
            return numberOfTargets == 1;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, if it has a single target, " + super.getRule();
    }

    @Override
    public CapturedByTheConsulateTriggeredAbility copy() {
        return new CapturedByTheConsulateTriggeredAbility(this);
    }
}

class CapturedByTheConsulateEffect extends OneShotEffect {

    public CapturedByTheConsulateEffect() {
        super(Outcome.Benefit);
        this.staticText = "change the target to enchanted creature if able";
    }

    public CapturedByTheConsulateEffect(final CapturedByTheConsulateEffect effect) {
        super(effect);
    }

    @Override
    public CapturedByTheConsulateEffect copy() {
        return new CapturedByTheConsulateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceEnchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceEnchantment != null) {
            StackObject stackObject = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
            if (stackObject != null) {
                Target target = stackObject.getStackAbility().getTargets().get(0);
                if (target != null) {
                    if (target.canTarget(stackObject.getControllerId(), sourceEnchantment.getAttachedTo(), source, game)) {
                        target.remove(target.getFirstTarget());
                        target.add(sourceEnchantment.getAttachedTo(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
