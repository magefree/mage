package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AustinYQM
 */
public final class AngelicCub extends CardImpl {

    public AngelicCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Angelic Cub becomes the target of a spell or ability for the first time each turn, put a +1/+1 counter on it.
        // As long as Angelic Cub has three or more +1/+1 counters on it, it has flying.
    }

    private AngelicCub(final AngelicCub card) {
        super(card);
    }

    @Override
    public AngelicCub copy() {
        return new AngelicCub(this);
    }
}
