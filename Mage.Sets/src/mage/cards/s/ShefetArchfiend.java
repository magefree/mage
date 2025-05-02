package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShefetArchfiend extends CardImpl {

    public ShefetArchfiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, all other creatures get -2/-2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, true
        ).setText("all other creatures get -2/-2 until end of turn")));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ShefetArchfiend(final ShefetArchfiend card) {
        super(card);
    }

    @Override
    public ShefetArchfiend copy() {
        return new ShefetArchfiend(this);
    }
}
