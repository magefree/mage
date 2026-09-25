package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VitoThornOfTheDuskRose extends CardImpl {

    public VitoThornOfTheDuskRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you gain life, target opponent loses that much life.
        Ability ability = new GainLifeControllerTriggeredAbility(new LoseLifeTargetEffect(SavedGainedLifeValue.MUCH), false, true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {3}{B}{B}: Creatures you control gain lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ), new ManaCostsImpl<>("{3}{B}{B}")));
    }

    private VitoThornOfTheDuskRose(final VitoThornOfTheDuskRose card) {
        super(card);
    }

    @Override
    public VitoThornOfTheDuskRose copy() {
        return new VitoThornOfTheDuskRose(this);
    }
}
