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
 * @author muz
 */
public final class YargleGoliathOfOtaria extends CardImpl {

    public YargleGoliathOfOtaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(9);
    }

    private YargleGoliathOfOtaria(final YargleGoliathOfOtaria card) {
        super(card);
    }

    @Override
    public YargleGoliathOfOtaria copy() {
        return new YargleGoliathOfOtaria(this);
    }
}
