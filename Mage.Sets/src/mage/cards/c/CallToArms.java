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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class CallToArms extends CardImpl {

    public CallToArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // As Call to Arms enters the battlefield, choose a color and an opponent.
        Effect effect = new ChooseColorEffect(Outcome.Detriment);
//        effect.setText("choose a color and an opponent");
        Ability ability = new AsEntersBattlefieldAbility(effect);
        effect = new ChooseOpponentEffect(Outcome.Benefit);
        effect.setText("then choose an opponent");
        ability.addEffect(effect);
        this.addAbility(ability);

        // White creatures get +1/+1 as long as the chosen color is the most common color among nontoken permanents the chosen player controls but isn't tied for most common.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CallToArmsEffect()));

        // When the chosen color isn't the most common color among nontoken permanents the chosen player controls or is tied for most common, sacrifice Call to Arms.
        this.addAbility(new SacAbility());
    }

    public CallToArms(final CallToArms card) {
        super(card);
    }

    @Override
    public CallToArms copy() {
        return new CallToArms(this);
    }
}

class CallToArmsEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public CallToArmsEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.Benefit);
        staticText = "The chosen player's maximum hand size is four";
    }

    public CallToArmsEffect(final CallToArmsEffect effect) {
        super(effect);
    }

    @Override
    public CallToArmsEffect copy() {
        return new CallToArmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
        if (permanent != null) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null) {
                ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
                Condition condition = new MostCommonColorCondition(color, true, new ControllerIdPredicate(playerId));
                if (condition.apply(game, source)) {
                    Effect effect = new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false);
                    return effect.apply(game, source);
                }
            }
        }
        return false;
    }
}

class SacAbility extends StateTriggeredAbility {

    public SacAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public SacAbility(final SacAbility ability) {
        super(ability);
    }

    @Override
    public SacAbility copy() {
        return new SacAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        UUID playerId = (UUID) game.getState().getValue(getSourceId() + ChooseOpponentEffect.VALUE_KEY);
        if (permanent != null) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null) {
                ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
                Condition condition = new MostCommonColorCondition(color, true, new ControllerIdPredicate(playerId));
                return !condition.apply(game, this);
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When the chosen color isn't the most common color among nontoken permanents the chosen player controls or is tied for most common, sacrifice {this}";
    }

}
