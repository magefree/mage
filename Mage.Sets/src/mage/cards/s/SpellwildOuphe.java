
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
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2 & L_J
 */
public final class SpellwildOuphe extends CardImpl {

    public SpellwildOuphe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Spells  that target Elderwood Scion cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellwildOupheCostReductionEffect()));
    }

    public SpellwildOuphe(final SpellwildOuphe card) {
        super(card);
    }

    @Override
    public SpellwildOuphe copy() {
        return new SpellwildOuphe(this);
    }
}

class SpellwildOupheCostReductionEffect extends CostModificationEffectImpl {

    private static final String effectText = "Spells that target {this} cost {2} less to cast";

    SpellwildOupheCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    SpellwildOupheCostReductionEffect(SpellwildOupheCostReductionEffect effect) {
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
        return false;
    }

    @Override
    public SpellwildOupheCostReductionEffect copy() {
        return new SpellwildOupheCostReductionEffect(this);
    }

}
