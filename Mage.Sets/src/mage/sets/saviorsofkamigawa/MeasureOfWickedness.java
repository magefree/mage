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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class MeasureOfWickedness extends CardImpl {

    private static final FilterCard filter = new FilterCard("another card");

    static {
        filter.add(new AnotherCardPredicate());
    }

    public MeasureOfWickedness(UUID ownerId) {
        super(ownerId, 82, "Measure of Wickedness", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "SOK";

        this.color.setBlack(true);

        // At the beginning of your end step, sacrifice Measure of Wickedness and you lose 8 life.        
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, null, false);
        Effect effect = new LoseLifeSourceControllerEffect(8);
        effect.setText("and you lose 8 life");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever another card is put into your graveyard from anywhere, target opponent gains control of Measure of Wickedness.
        ability = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new MeasureOfWickednessControlSourceEffect(), false, filter, TargetController.YOU);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public MeasureOfWickedness(final MeasureOfWickedness card) {
        super(card);
    }

    @Override
    public MeasureOfWickedness copy() {
        return new MeasureOfWickedness(this);
    }
}

class MeasureOfWickednessControlSourceEffect extends ContinuousEffectImpl {

    public MeasureOfWickednessControlSourceEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public MeasureOfWickednessControlSourceEffect(final MeasureOfWickednessControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public MeasureOfWickednessControlSourceEffect copy() {
        return new MeasureOfWickednessControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (permanent != null && targetOpponent != null) {
                permanent.changeControllerId(targetOpponent.getId(), game);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}
