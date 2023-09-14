package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author gravitybone
 * @author Aquid
 */
public final class AnrakyrTheTraveller extends CardImpl {

    public AnrakyrTheTraveller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT, CardType.CREATURE }, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private AnrakyrTheTraveller(final AnrakyrTheTraveller card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new AnrakyrTheTraveller(this);
    }
}
