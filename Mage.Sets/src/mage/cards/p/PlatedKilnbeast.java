package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlatedKilnbeast extends CardImpl {

    public PlatedKilnbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private PlatedKilnbeast(final PlatedKilnbeast card) {
        super(card);
    }

    @Override
    public PlatedKilnbeast copy() {
        return new PlatedKilnbeast(this);
    }
}
