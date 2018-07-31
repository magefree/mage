
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class ElderwoodScion extends CardImpl {

    public ElderwoodScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Spells you cast that target Elderwood Scion cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ElderwoodScionCostReductionEffect()));
        // Spells your opponents cast that target Elderwood Scion cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ElderwoodScionCostReductionEffect2()));
    }

    public ElderwoodScion(final ElderwoodScion card) {
        super(card);
    }

    @Override
    public ElderwoodScion copy() {
        return new ElderwoodScion(this);
    }
}

class ElderwoodScionCostReductionEffect extends CostModificationEffectImpl {

    private static final String effectText = "Spells you cast that target {this} cost {2} less to cast";

    ElderwoodScionCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    ElderwoodScionCostReductionEffect(ElderwoodScionCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                    Mode mode = abilityToModify.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetUUID : target.getTargets()) {
                            if (targetUUID.equals(source.getSourceId())) {
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
    public ElderwoodScionCostReductionEffect copy() {
        return new ElderwoodScionCostReductionEffect(this);
    }

}

class ElderwoodScionCostReductionEffect2 extends CostModificationEffectImpl {

    private static final String effectText = "Spells your opponents cast that target {this} cost {2} more to cast";

    ElderwoodScionCostReductionEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = effectText;
    }

    ElderwoodScionCostReductionEffect2(ElderwoodScionCostReductionEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.increaseCost(spellAbility, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (Target target : abilityToModify.getTargets()) {
                    for (UUID targetUUID : target.getTargets()) {
                        if (targetUUID.equals(source.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ElderwoodScionCostReductionEffect2 copy() {
        return new ElderwoodScionCostReductionEffect2(this);
    }

}
