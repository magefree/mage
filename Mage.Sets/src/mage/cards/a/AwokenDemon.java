package mage.cards.a;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AwokenDemon extends CardImpl {

    public AwokenDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.transformable = true;
        this.nightCard = true;
    }

    private AwokenDemon(final AwokenDemon card) {
        super(card);
    }

    @Override
    public AwokenDemon copy() {
        return new AwokenDemon(this);
    }
}
