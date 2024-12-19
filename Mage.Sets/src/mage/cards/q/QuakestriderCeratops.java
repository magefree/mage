package mage.cards.q;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuakestriderCeratops extends CardImpl {

    public QuakestriderCeratops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(12);
        this.toughness = new MageInt(8);
    }

    private QuakestriderCeratops(final QuakestriderCeratops card) {
        super(card);
    }

    @Override
    public QuakestriderCeratops copy() {
        return new QuakestriderCeratops(this);
    }
}
