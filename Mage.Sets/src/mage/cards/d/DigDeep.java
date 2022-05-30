package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class DigDeep extends CardImpl {

    public DigDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");
        
        this.subtype.add(SubType.ADVENTURE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Choose target creature. Mill four cards, then put a +1/+1 counter on that creature for each creature card milled this way.
    }

    private DigDeep(final DigDeep card) {
        super(card);
    }

    @Override
    public DigDeep copy() {
        return new DigDeep(this);
    }
}
