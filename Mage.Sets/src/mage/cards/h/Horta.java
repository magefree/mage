package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Horta extends CardImpl {

    public Horta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Horta(final Horta card) {
        super(card);
    }

    @Override
    public Horta copy() {
        return new Horta(this);
    }
}
