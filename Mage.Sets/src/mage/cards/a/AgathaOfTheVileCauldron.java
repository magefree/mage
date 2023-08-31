package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AgathaOfTheVileCauldron extends CardImpl {

    public AgathaOfTheVileCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Activated abilities of creatures you control cost X less to activate, where X is Agatha of the Vile Cauldron's power. This effect can't reduce the mana in that cost to less than one mana.
        this.addAbility(new SimpleStaticAbility(new AgathaOfTheVileCauldronEffect()));

        // {4}{R}{G}: Other creatures you control get +1/+1 and gain trample and haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, true)
                        .setText("Other creatures you control get +1/+1"),
                new ManaCostsImpl<>("{4}{R}{G}"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        ).setText("and gain trample"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        ).setText("and haste until end of turn"));

        this.addAbility(ability);
    }

    private AgathaOfTheVileCauldron(final AgathaOfTheVileCauldron card) {
        super(card);
    }

    @Override
    public AgathaOfTheVileCauldron copy() {
        return new AgathaOfTheVileCauldron(this);
    }
}

class AgathaOfTheVileCauldronEffect extends CostModificationEffectImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    AgathaOfTheVileCauldronEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Activated abilities of creatures you control cost {X} less to activate, "
                + "where X is {this}'s power. "
                + "This effect can't reduce the mana in that cost to less than one mana.";
    }

    private AgathaOfTheVileCauldronEffect(final AgathaOfTheVileCauldronEffect effect) {
        super(effect);
    }

    @Override
    public AgathaOfTheVileCauldronEffect copy() {
        return new AgathaOfTheVileCauldronEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int amount = xValue.calculate(game, source, this);
        if (amount <= 0) {
            return true;
        }

        int reduceMax = CardUtil.calculateActualPossibleGenericManaReduction(
                abilityToModify.getManaCostsToPay().getMana(), amount, 1
        );
        if (reduceMax < 1) {
            return true;
        }

        CardUtil.reduceCost(abilityToModify, reduceMax);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        // Activated abilities you control
        if (abilityToModify.getAbilityType() != AbilityType.ACTIVATED
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        Permanent permanent = abilityToModify.getSourcePermanentOrLKI(game);
        // Only for abilities of creatures.
        return permanent != null && permanent.isCreature(game);
    }
}
