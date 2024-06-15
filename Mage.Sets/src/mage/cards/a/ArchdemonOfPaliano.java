package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author tiera3 - based on ChardalynDragon
 * note - draftmatters ability not implemented
 */
public final class ArchdemonOfPaliano extends CardImpl {

    public ArchdemonOfPaliano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private ArchdemonOfPaliano(final ArchdemonOfPaliano card) {
        super(card);
    }

    @Override
    public ArchdemonOfPaliano copy() {
        return new ArchdemonOfPaliano(this);
    }
}
