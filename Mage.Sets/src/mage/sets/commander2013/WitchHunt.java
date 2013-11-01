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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continious.PlayersCantGainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class WitchHunt extends CardImpl<WitchHunt> {

    public WitchHunt(UUID ownerId) {
        super(ownerId, 133, "Witch Hunt", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
        this.expansionSetCode = "C13";

        this.color.setRed(true);

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayersCantGainLifeEffect()));
        // At the beginning of your upkeep, Witch Hunt deals 4 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageControllerEffect(4), TargetController.YOU, false));
        // At the beginning of your end step, target opponent chosen at random gains control of Witch Hunt.
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new WitchHuntEffect(), TargetController.YOU, null, false);
        Target target = new TargetOpponent(true);
        target.setRandom(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public WitchHunt(final WitchHunt card) {
        super(card);
    }

    @Override
    public WitchHunt copy() {
        return new WitchHunt(this);
    }
}

class WitchHuntEffect extends ContinuousEffectImpl<WitchHuntEffect> {

    public WitchHuntEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent chosen at random gains control of {this}";
    }

    public WitchHuntEffect(final WitchHuntEffect effect) {
        super(effect);
    }

    @Override
    public WitchHuntEffect copy() {
        return new WitchHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.changeControllerId(this.getTargetPointer().getFirst(game, source), game);
        }
        return false;
    }

}
