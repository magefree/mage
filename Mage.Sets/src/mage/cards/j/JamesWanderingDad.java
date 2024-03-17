package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class JamesWanderingDad extends CardImpl {

    public JamesWanderingDad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // e: Add {C}{C}. Spend this mana only to activate abilities.
    }

    private JamesWanderingDad(final JamesWanderingDad card) {
        super(card);
    }

    @Override
    public JamesWanderingDad copy() {
        return new JamesWanderingDad(this);
    }
}
