package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author AustinYQM
 */
public final class AlandraSkyDreamer extends CardImpl {

    public AlandraSkyDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you draw your second card each turn, create a 2/2 blue Drake creature token with flying.
        // Whenever you draw your fifth card each turn, Alandra, Sky Dreamer and Drakes you control each get +X/+X until end of turn, where X is the number of cards in your hand.
    }

    private AlandraSkyDreamer(final AlandraSkyDreamer card) {
        super(card);
    }

    @Override
    public AlandraSkyDreamer copy() {
        return new AlandraSkyDreamer(this);
    }
}
