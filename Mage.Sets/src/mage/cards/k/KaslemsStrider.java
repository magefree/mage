package mage.cards.k;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaslemsStrider extends CardImpl {

    public KaslemsStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.nightCard = true;
        this.color.setGreen(true);
    }

    private KaslemsStrider(final KaslemsStrider card) {
        super(card);
    }

    @Override
    public KaslemsStrider copy() {
        return new KaslemsStrider(this);
    }
}
