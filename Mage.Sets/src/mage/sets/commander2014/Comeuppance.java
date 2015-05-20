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
package mage.sets.commander2014;

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
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public class Comeuppance extends CardImpl {

    public Comeuppance(UUID ownerId) {
        super(ownerId, 4, "Comeuppance", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "C14";


        // Prevent all damage that would be dealt to you and planeswalkers you control this turn by sources you don't control. If damage from a creature source is prevented this way, Comeuppance deals that much damage to that creature. If damage from a noncreature source is prevented this way, Comeuppance deals that much damage to the source's controller.
        this.getSpellAbility().addEffect(new ComeuppanceEffect());

    }

    public Comeuppance(final Comeuppance card) {
        super(card);
    }

    @Override
    public Comeuppance copy() {
        return new Comeuppance(this);
    }
}
class ComeuppanceEffect extends PreventionEffectImpl {

    public ComeuppanceEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "Prevent all damage that would be dealt to you and planeswalkers you control this turn by sources you don't control. If damage from a creature source is prevented this way, {this} deals that much damage to that creature. If damage from a noncreature source is prevented this way, {this} deals that much damage to the source's controller";
    }

    public ComeuppanceEffect(final ComeuppanceEffect effect) {
        super(effect);
    }

    @Override
    public ComeuppanceEffect copy() {
        return new ComeuppanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        if (preventionData.getPreventedDamage() > 0) {
            MageObject damageDealingObject = game.getObject(event.getSourceId());
            UUID objectControllerId = null;
            if (damageDealingObject instanceof Permanent) {
                if (((Permanent) damageDealingObject).getCardType().contains(CardType.CREATURE)) {
                    ((Permanent) damageDealingObject).damage(preventionData.getPreventedDamage(), source.getSourceId(), game, false, true);
                } else {
                    objectControllerId = ((Permanent) damageDealingObject).getControllerId();
                }
            } else if (damageDealingObject instanceof Ability) {
                objectControllerId = ((Ability) damageDealingObject).getControllerId();
            } else if (damageDealingObject instanceof Spell) {
                objectControllerId = ((Spell) damageDealingObject).getControllerId();
            }
            if (objectControllerId != null) {
                Player objectController = game.getPlayer(objectControllerId);
                if (objectController != null) {
                    objectController.damage(preventionData.getPreventedDamage(), source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return  false;
        }
        boolean catched = false;
        if (event.getTargetId().equals(source.getControllerId())) {
            catched = true;
        } else {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null &&
                    targetPermanent.getControllerId().equals(source.getControllerId()) &&
                    targetPermanent.getCardType().contains(CardType.PLANESWALKER)) {
                catched = true;
            }
        }
        if (catched) {
            MageObject damageSource = game.getObject(event.getSourceId());
            if (damageSource instanceof StackObject) {
                return !((StackObject) damageSource).getControllerId().equals(source.getControllerId());
            } else if (damageSource instanceof Permanent) {
                return !((Permanent) damageSource).getControllerId().equals(source.getControllerId());
            } else if (damageSource instanceof Card) {
                return !((Card) damageSource).getOwnerId().equals(source.getControllerId());
            }
            Logger.getLogger(Comeuppance.class).error("Comeuppance: could not define source objects controller - " + (damageSource != null ? damageSource.getName(): "null"));
        }
        return false;
    }

}
