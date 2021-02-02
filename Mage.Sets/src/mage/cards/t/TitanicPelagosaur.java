package mage.cards.t;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class TitanicPelagosaur extends CardImpl {

    public TitanicPelagosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);
    }

    private TitanicPelagosaur(final TitanicPelagosaur card) {
        super(card);
    }

    @Override
    public TitanicPelagosaur copy() {
        return new TitanicPelagosaur(this);
    }
}
