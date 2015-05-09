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
package mage.sets.magic2010;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCreatureOrPlayer;

/**
 * @author noxx
 */
public class HarmsWay extends CardImpl {

    public HarmsWay(UUID ownerId) {
        super(ownerId, 14, "Harm's Way", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "M10";

        this.color.setWhite(true);

        // The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead.
        this.getSpellAbility().addEffect(new HarmsWayPreventDamageTargetEffect());
        this.getSpellAbility().addTarget(new TargetSource());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public HarmsWay(final HarmsWay card) {
        super(card);
    }

    @Override
    public HarmsWay copy() {
        return new HarmsWay(this);
    }
}

class HarmsWayPreventDamageTargetEffect extends PreventionEffectImpl {

    public HarmsWayPreventDamageTargetEffect() {
        super(Duration.EndOfTurn, 2, false, true);
        staticText = "The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead";
    }

    public HarmsWayPreventDamageTargetEffect(final HarmsWayPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public HarmsWayPreventDamageTargetEffect copy() {
        return new HarmsWayPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        // deal damage now
        if (preventionData.getPreventedDamage() > 0) {
            UUID redirectTo = source.getTargets().get(1).getFirstTarget();
            Permanent permanent = game.getPermanent(redirectTo);
            if (permanent != null) {
                game.informPlayers("Dealing " + preventionData.getPreventedDamage() + " to " + permanent.getLogName() + " instead");
                // keep the original source id as it is redirecting
                permanent.damage(preventionData.getPreventedDamage(), event.getSourceId(), game, false, true);
            }
            Player player = game.getPlayer(redirectTo);
            if (player != null) {
                game.informPlayers("Dealing " + preventionData.getPreventedDamage() + " to " + player.getLogName() + " instead");
                // keep the original source id as it is redirecting
                player.damage(preventionData.getPreventedDamage(), event.getSourceId(), game, false, true);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }

            if (!object.getId().equals(source.getFirstTarget())
                    && (!(object instanceof Spell) || !((Spell) object).getSourceId().equals(source.getFirstTarget()))) {
                return false;
            }

            // check target
            //   check permanent first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (permanent.getControllerId().equals(source.getControllerId())) {
                    // it's your permanent
                    return true;
                }
            }
            //   check player
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                if (player.getId().equals(source.getControllerId())) {
                    // it is you
                    return true;
                }
            }
        }
        return false;
    }

}

