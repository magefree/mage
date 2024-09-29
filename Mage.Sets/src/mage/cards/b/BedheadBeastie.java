package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BedheadBeastie extends CardImpl {

    public BedheadBeastie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility());

        // Mountaincycling {2}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private BedheadBeastie(final BedheadBeastie card) {
        super(card);
    }

    @Override
    public BedheadBeastie copy() {
        return new BedheadBeastie(this);
    }
}
