
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author JayDi85
 */
public final class SunCollaredRaptor extends CardImpl {

    public SunCollaredRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {2}{R}: Sun-Collared Raptor gets +3/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(3, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{R}"))
        );
    }

    private SunCollaredRaptor(final SunCollaredRaptor card) {
        super(card);
    }

    @Override
    public SunCollaredRaptor copy() {
        return new SunCollaredRaptor(this);
    }
}