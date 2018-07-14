
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
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
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class MonasterySiege extends CardImpl {

    public MonasterySiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

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
                    for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                        Mode mode = abilityToModify.getModes().get(modeId);
                        for (Target target : mode.getTargets()) {
                            for (UUID targetUUID : target.getTargets()) {
                                if (targetUUID.equals(source.getControllerId())) {
                                    return true;
                                }
                                Permanent permanent = game.getPermanent(targetUUID);
                                if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
                                    return true;
                                }
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
