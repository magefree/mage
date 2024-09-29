package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class NightOfTheSweetsRevenge extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods");

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Foods you control", xValue);

    public NightOfTheSweetsRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        

        // When Night of the Sweets' Revenge enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Foods you control have "{T}: Add {G}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new GreenManaAbility(), Duration.WhileOnBattlefield, filter
        )));

        // {5}{G}{G}, Sacrifice Night of the Sweets' Revenge: Creatures you control get +X/+X until end of turn, where X is the number of Foods you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostControlledEffect(
                        xValue, xValue, Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES, false
                ).setText("creatures you control get +X/+X until end of turn, where X is the number of Foods you control"),
                new ManaCostsImpl<>("{5}{G}{G}")
        ).addHint(hint);
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private NightOfTheSweetsRevenge(final NightOfTheSweetsRevenge card) {
        super(card);
    }

    @Override
    public NightOfTheSweetsRevenge copy() {
        return new NightOfTheSweetsRevenge(this);
    }
}
