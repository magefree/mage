
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class AlphaTyrranax extends CardImpl {

    public AlphaTyrranax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
    }

    private AlphaTyrranax(final AlphaTyrranax card) {
        super(card);
    }

    @Override
    public AlphaTyrranax copy() {
        return new AlphaTyrranax(this);
    }

}
