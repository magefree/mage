package mage.cards.c;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CatacombCrocodile extends CardImpl {

    public CatacombCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);
    }

    private CatacombCrocodile(final CatacombCrocodile card) {
        super(card);
    }

    @Override
    public CatacombCrocodile copy() {
        return new CatacombCrocodile(this);
    }
}
