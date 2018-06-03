
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class GrandArbiterAugustinIV extends CardImpl {

    private static final FilterCard filterWhite = new FilterCard("White spells");
    private static final FilterCard filterBlue = new FilterCard("Blue spells");
    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public GrandArbiterAugustinIV(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // White spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filterWhite, 1)));
        // Blue spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filterBlue, 1)));
        // Spells your opponents cast cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrandArbiterAugustinIVCostIncreaseEffect()));
    }

    public GrandArbiterAugustinIV(final GrandArbiterAugustinIV card) {
        super(card);
    }

    @Override
    public GrandArbiterAugustinIV copy() {
        return new GrandArbiterAugustinIV(this);
    }
}

class GrandArbiterAugustinIVCostIncreaseEffect extends CostModificationEffectImpl {

    private static final String effectText = "Spells your opponents cast cost {1} more to cast";

    GrandArbiterAugustinIVCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = effectText;
    }

    GrandArbiterAugustinIVCostIncreaseEffect(GrandArbiterAugustinIVCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GrandArbiterAugustinIVCostIncreaseEffect copy() {
        return new GrandArbiterAugustinIVCostIncreaseEffect(this);
    }

}
