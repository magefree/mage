package mage.cards.h;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeroOfTheGames extends CardImpl {

    public HeroOfTheGames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast a spell that targets Hero of the Games, creatures you control get +1/+0 until end of turn.
        this.addAbility(new HeroicAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn), false, false
        ));
    }

    private HeroOfTheGames(final HeroOfTheGames card) {
        super(card);
    }

    @Override
    public HeroOfTheGames copy() {
        return new HeroOfTheGames(this);
    }
}
