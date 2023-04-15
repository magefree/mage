package mage.cards.f;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FurnaceHostCharger extends CardImpl {

    public FurnaceHostCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Mountaincycling {2}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private FurnaceHostCharger(final FurnaceHostCharger card) {
        super(card);
    }

    @Override
    public FurnaceHostCharger copy() {
        return new FurnaceHostCharger(this);
    }
}
