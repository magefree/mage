package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class FreeBorgRevolutionaries extends CardImpl {

    public FreeBorgRevolutionaries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.BORG);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When this creature enters, creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new BoostControlledEffect(1, 1, Duration.EndOfTurn)
        ));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private FreeBorgRevolutionaries(final FreeBorgRevolutionaries card) {
        super(card);
    }

    @Override
    public FreeBorgRevolutionaries copy() {
        return new FreeBorgRevolutionaries(this);
    }
}
