
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class YargleGluttonOfUrborg extends CardImpl {

    public YargleGluttonOfUrborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(9);
        this.toughness = new MageInt(3);
    }

    private YargleGluttonOfUrborg(final YargleGluttonOfUrborg card) {
        super(card);
    }

    @Override
    public YargleGluttonOfUrborg copy() {
        return new YargleGluttonOfUrborg(this);
    }
}
