package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HamzaGuardianOfArashin extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1);
    private static final Hint hint = new ValueHint("Creatures you control with +1/+1 counter on them", xValue);

    public HamzaGuardianOfArashin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each creature you control with a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Creature spells you cast cost {1} less to cast for each creature you control with a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new HamzaGuardianOfArashinEffect(xValue)));
    }

    private HamzaGuardianOfArashin(final HamzaGuardianOfArashin card) {
        super(card);
    }

    @Override
    public HamzaGuardianOfArashin copy() {
        return new HamzaGuardianOfArashin(this);
    }
}

class HamzaGuardianOfArashinEffect extends CostModificationEffectImpl {

    private final DynamicValue xValue;

    HamzaGuardianOfArashinEffect(DynamicValue xValue) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Creature spells you cast cost {1} less to cast " +
                "for each creature you control with a +1/+1 counter on it";
        this.xValue = xValue;
    }

    private HamzaGuardianOfArashinEffect(HamzaGuardianOfArashinEffect effect) {
        super(effect);
        this.xValue = effect.xValue;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int amount = xValue.calculate(game, source, this);
        if (amount > 0) {
            CardUtil.adjustCost((SpellAbility) abilityToModify, amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }
        Card sourceCard = game.getCard(abilityToModify.getSourceId());
        return sourceCard != null
                && abilityToModify.isControlledBy(source.getControllerId())
                && sourceCard.isCreature(game);
    }

    @Override
    public HamzaGuardianOfArashinEffect copy() {
        return new HamzaGuardianOfArashinEffect(this);
    }
}
