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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class HumbleDefector extends CardImpl {

    public HumbleDefector(UUID ownerId) {
        super(ownerId, 104, "Humble Defector", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Draw two cards. Target opponent gains control of Humble Defector. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new HumbleDefectorEffect(), new TapSourceCost(), MyTurnCondition.getInstance());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public HumbleDefector(final HumbleDefector card) {
        super(card);
    }

    @Override
    public HumbleDefector copy() {
        return new HumbleDefector(this);
    }
}

class HumbleDefectorEffect extends OneShotEffect {

    public HumbleDefectorEffect() {
        super(Outcome.Discard);
        this.staticText = "Draw two cards. Target opponent gains control of {this}.";
    }

    public HumbleDefectorEffect(final HumbleDefectorEffect effect) {
        super(effect);
    }

    @Override
    public HumbleDefectorEffect copy() {
        return new HumbleDefectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());        
        if (controller != null) {
            controller.drawCards(2, game);
        }
        Permanent humbleDefector = (Permanent) source.getSourceObjectIfItStillExists(game);
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetOpponent != null && humbleDefector != null) {
            ContinuousEffect effect = new HumbleDefectorControlSourceEffect();
            game.addEffect(effect, source);
            game.informPlayers(humbleDefector.getName() + " is now controlled by " + targetOpponent.getLogName());
            return true;
        }
        return false;
    }
}

class HumbleDefectorControlSourceEffect extends ContinuousEffectImpl {

    public HumbleDefectorControlSourceEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public HumbleDefectorControlSourceEffect(final HumbleDefectorControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public HumbleDefectorControlSourceEffect copy() {
        return new HumbleDefectorControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && targetOpponent != null) {
                permanent.changeControllerId(targetOpponent.getId(), game);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}