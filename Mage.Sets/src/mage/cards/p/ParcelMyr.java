package mage.cards.p;

import mage.MageInt;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParcelMyr extends CardImpl {

    public ParcelMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.CLUE);
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}, Sacrifice Parcel Myr: Draw a card
        this.addAbility(new ClueAbility(true));
    }

    private ParcelMyr(final ParcelMyr card) {
        super(card);
    }

    @Override
    public ParcelMyr copy() {
        return new ParcelMyr(this);
    }
}
