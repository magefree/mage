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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public class MirrorStrike extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked creature");

    static {
        filter.add(new UnblockedPredicate());
    }

    public MirrorStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // All combat damage that would be dealt to you this turn by target unblocked creature is dealt to its controller instead.
        this.getSpellAbility().addEffect(new MirrorStrikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public MirrorStrike(final MirrorStrike card) {
        super(card);
    }

    @Override
    public MirrorStrike copy() {
        return new MirrorStrike(this);
    }
}

class MirrorStrikeEffect extends ReplacementEffectImpl {

    public MirrorStrikeEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "All combat damage that would be dealt to you this turn by target unblocked creature is dealt to its controller instead";
    }

    public MirrorStrikeEffect(final MirrorStrikeEffect effect) {
        super(effect);
    }

    @Override
    public MirrorStrikeEffect copy() {
        return new MirrorStrikeEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
            if (targetPermanent != null) {
                Player targetsController = game.getPlayer(targetPermanent.getControllerId());
                if (targetsController != null) {
                    targetsController.damage(damageEvent.getAmount(), damageEvent.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && targetPermanent != null) {
            return (damageEvent.isCombatDamage() && controller.getId() == damageEvent.getTargetId() && targetPermanent.getId() == damageEvent.getSourceId());
        }
        return false;
    }
}
