package mage.cards.h;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class HeroOfTheWinds extends CardImpl {

    public HeroOfTheWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell that targets Hero of the Winds, creatures you control get +1/+0 until end of turn.
        this.addAbility(new HeroicAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ), false, false));
    }

    private HeroOfTheWinds(final HeroOfTheWinds card) {
        super(card);
    }

    @Override
    public HeroOfTheWinds copy() {
        return new HeroOfTheWinds(this);
    }
}
