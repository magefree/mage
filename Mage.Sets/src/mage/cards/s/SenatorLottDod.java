
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public final class SenatorLottDod extends CardImpl {

    public SenatorLottDod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NEIMOIDIAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spells your opponents cast that target you cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SenatorLottDodSpellsTargetingYouCostReductionEffect()));

        // Spell your opponents cast that target a creature you control cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SenatorLottDodSpellsTargetingCreatureCostReductionEffect()));

    }

    public SenatorLottDod(final SenatorLottDod card) {
        super(card);
    }

    @Override
    public SenatorLottDod copy() {
        return new SenatorLottDod(this);
    }
}

class SenatorLottDodSpellsTargetingCreatureCostReductionEffect extends CostModificationEffectImpl {

    public SenatorLottDodSpellsTargetingCreatureCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Spell your opponents cast that target a creature you control cost {1} more to cast.";
    }

    protected SenatorLottDodSpellsTargetingCreatureCostReductionEffect(SenatorLottDodSpellsTargetingCreatureCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                    Mode mode = abilityToModify.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetUUID : target.getTargets()) {
                            Permanent permanent = game.getPermanent(targetUUID);
                            if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
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
    public SenatorLottDodSpellsTargetingCreatureCostReductionEffect copy() {
        return new SenatorLottDodSpellsTargetingCreatureCostReductionEffect(this);
    }
}

class SenatorLottDodSpellsTargetingYouCostReductionEffect extends CostModificationEffectImpl {

    public SenatorLottDodSpellsTargetingYouCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Spells your opponents cast that target you cost {2} more to cast.";
    }

    protected SenatorLottDodSpellsTargetingYouCostReductionEffect(SenatorLottDodSpellsTargetingYouCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                    Mode mode = abilityToModify.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetUUID : target.getTargets()) {
                            Player player = game.getPlayer(targetUUID);
                            if (player != null && player.getId().equals(source.getControllerId())) {
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
    public SenatorLottDodSpellsTargetingYouCostReductionEffect copy() {
        return new SenatorLottDodSpellsTargetingYouCostReductionEffect(this);
    }
}
