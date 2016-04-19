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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class ChannelHarm extends CardImpl {

    public ChannelHarm(UUID ownerId) {
        super(ownerId, 7, "Channel Harm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{5}{W}");
        this.expansionSetCode = "FRF";

        // Prevent all damage that would be dealt to you and permanents you control this turn by sources you don't control. If damage is prevented this way, you may have Channel Harm deal that much damage to target creature.
        this.getSpellAbility().addEffect(new ChannelHarmEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public ChannelHarm(final ChannelHarm card) {
        super(card);
    }

    @Override
    public ChannelHarm copy() {
        return new ChannelHarm(this);
    }
}

class ChannelHarmEffect extends PreventionEffectImpl {

    ChannelHarmEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "Prevent all damage that would be dealt to you and permanents you control this turn by sources you don't control. If damage is prevented this way, you may have {this} deal that much damage to target creature";
    }

    ChannelHarmEffect(final ChannelHarmEffect effect) {
        super(effect);
    }

    @Override
    public ChannelHarmEffect copy() {
        return new ChannelHarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        if (preventionData.getPreventedDamage() > 0) {
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(preventionData.getPreventedDamage(), source.getSourceId(), game, false, true);
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if ((targetPermanent != null && targetPermanent.getControllerId().equals(source.getControllerId()))
                    || event.getTargetId().equals(source.getControllerId())) {
                MageObject damageSource = game.getObject(event.getSourceId());
                if (damageSource instanceof Controllable) {
                    return !((Controllable) damageSource).getControllerId().equals(source.getControllerId());
                }
                else if (damageSource instanceof Card) {
                    return !((Card) damageSource).getOwnerId().equals(source.getControllerId());
                }
            }
        }
        return false;
    }
}
