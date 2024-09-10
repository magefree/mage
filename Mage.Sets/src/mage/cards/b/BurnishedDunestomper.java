package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurnishedDunestomper extends CardImpl {

    public BurnishedDunestomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private BurnishedDunestomper(final BurnishedDunestomper card) {
        super(card);
    }

    @Override
    public BurnishedDunestomper copy() {
        return new BurnishedDunestomper(this);
    }
}
