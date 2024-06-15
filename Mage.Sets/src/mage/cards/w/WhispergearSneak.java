package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author tiera3 - based on PrizefighterConstruct
 * note - draftmatters ability not implemented
 */
public final class WhispergearSneak extends CardImpl {

    public WhispergearSneak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    private WhispergearSneak(final WhispergearSneak card) {
        super(card);
    }

    @Override
    public WhispergearSneak copy() {
        return new WhispergearSneak(this);
    }
}
