package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BorealElemental extends CardImpl {

    public BorealElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents cast that target Boreal Elemental cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new BorealElementalCostIncreaseEffect()));
    }

    private BorealElemental(final BorealElemental card) {
        super(card);
    }

    @Override
    public BorealElemental copy() {
        return new BorealElemental(this);
    }
}

class BorealElementalCostIncreaseEffect extends CostModificationEffectImpl {

    BorealElementalCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target {this} cost {2} more to cast";
    }

    private BorealElementalCostIncreaseEffect(BorealElementalCostIncreaseEffect effect) {
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
        if (!(abilityToModify instanceof SpellAbility)
                || !game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }
        return abilityToModify
                .getModes()
                .getSelectedModes()
                .stream()
                .map(uuid -> abilityToModify.getModes().get(uuid))
                .anyMatch(mode -> mode
                        .getTargets()
                        .stream()
                        .anyMatch(target -> target
                                .getTargets()
                                .stream()
                                .anyMatch(uuid -> uuid.equals(source.getSourceId()))
                        )
                );
    }

    @Override
    public BorealElementalCostIncreaseEffect copy() {
        return new BorealElementalCostIncreaseEffect(this);
    }

}
