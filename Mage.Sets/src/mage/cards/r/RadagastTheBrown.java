package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author bobby-mccann
 */
public final class RadagastTheBrown extends CardImpl {

    public RadagastTheBrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Radagast the Brown or another nontoken creature enters the battlefield under your control,
        // look at the top X cards of your library, where X is that creature's mana value.
        // You may reveal a creature card from among them that doesn't share a creature type with a creature you control
        // and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
    }

    private RadagastTheBrown(final RadagastTheBrown card) {
        super(card);
    }

    @Override
    public RadagastTheBrown copy() {
        return new RadagastTheBrown(this);
    }
}
