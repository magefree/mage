package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PlainscyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EaglesOfTheNorth extends CardImpl {

    public EaglesOfTheNorth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Eagles of the North enters the battlefield, creatures you control get +1/+0 and gain first strike until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ).setText("creatures you control get +1/+0"));
        ability.addEffect(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain first strike until end of turn"));
        this.addAbility(ability);

        // Plainscycling {1}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private EaglesOfTheNorth(final EaglesOfTheNorth card) {
        super(card);
    }

    @Override
    public EaglesOfTheNorth copy() {
        return new EaglesOfTheNorth(this);
    }
}
