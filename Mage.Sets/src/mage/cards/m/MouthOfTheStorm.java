package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
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
public final class MouthOfTheStorm extends CardImpl {

    public MouthOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // When this creature enters, creatures your opponents control get -3/-0 until your next turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
                -3, 0, Duration.UntilYourNextTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        )));
    }

    private MouthOfTheStorm(final MouthOfTheStorm card) {
        super(card);
    }

    @Override
    public MouthOfTheStorm copy() {
        return new MouthOfTheStorm(this);
    }
}
