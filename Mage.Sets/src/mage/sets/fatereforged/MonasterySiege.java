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
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class MonasterySiege extends CardImpl {

    public MonasterySiege(UUID ownerId) {
        super(ownerId, 43, "Monastery Siege", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "FRF";

        // As Monastery Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?", "Khans", "Dragons"), null,
                "As {this} enters the battlefield, choose Khans or Dragons.", ""));
        
        // * Khans - At the beginning of your draw step, draw an additional card, then discard a card.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfDrawTriggeredAbility(new DrawDiscardControllerEffect(1, 1), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                "&bull; Khans &mdash; At the beginning of your draw step, draw an additional card, then discard a card."));
        
        // * Dragons - Spells your opponents cast that target you or a permanent you control cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MonasterySiegeCostIncreaseEffect()));
    }

    public MonasterySiege(final MonasterySiege card) {
        super(card);
    }

    @Override
    public MonasterySiege copy() {
        return new MonasterySiege(this);
    }
}

class MonasterySiegeCostIncreaseEffect extends CostModificationEffectImpl {

    MonasterySiegeCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "&bull; Dragons &mdash; Spells your opponents cast that target you or a permanent you control cost {2} more to cast";
    }

    MonasterySiegeCostIncreaseEffect(MonasterySiegeCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (new ModeChoiceSourceCondition("Dragons").apply(game, source)) {
            if (abilityToModify instanceof SpellAbility) {
                if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                    for (Target target : abilityToModify.getTargets()) {
                        for (UUID targetUUID : target.getTargets()) {
                            if (targetUUID.equals(source.getControllerId())) {
                                return true;
                            }
                            Permanent permanent = game.getPermanent(targetUUID);
                            if (permanent != null && permanent.getControllerId().equals(source.getControllerId())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public MonasterySiegeCostIncreaseEffect copy() {
        return new MonasterySiegeCostIncreaseEffect(this);
    }
}
