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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSource;

/**
 *
 * @author LevelX2
 */
public class DeflectingPalm extends CardImpl {

    public DeflectingPalm(UUID ownerId) {
        super(ownerId, 173, "Deflecting Palm", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{R}{W}");
        this.expansionSetCode = "KTK";


        // The next time a source of your choice would deal damage to you this turn, prevent that damage. If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.
        this.getSpellAbility().addEffect(new DeflectingPalmEffect());
    }

    public DeflectingPalm(final DeflectingPalm card) {
        super(card);
    }

    @Override
    public DeflectingPalm copy() {
        return new DeflectingPalm(this);
    }
}

class DeflectingPalmEffect extends PreventionEffectImpl {

    private final TargetSource target;
    
    public DeflectingPalmEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "The next time a source of your choice would deal damage to you this turn, prevent that damage. If damage is prevented this way, {this} deals that much damage to that source's controller";
        this.target = new TargetSource();
    }

    public DeflectingPalmEffect(final DeflectingPalmEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public DeflectingPalmEffect copy() {
        return new DeflectingPalmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {        
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard(); // only one use
        if (preventionData.getPreventedDamage() > 0) {
            MageObject damageDealingObject = game.getObject(target.getFirstTarget());
            UUID objectControllerId = null;
            if (damageDealingObject instanceof Permanent) {
                objectControllerId = ((Permanent) damageDealingObject).getControllerId();
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
        if (!this.used && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }
}
