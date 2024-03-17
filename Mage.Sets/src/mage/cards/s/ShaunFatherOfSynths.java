package mage.cards.s;

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
public final class ShaunFatherOfSynths extends CardImpl {

    public ShaunFatherOfSynths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you attack, you may create a tapped and attacking token that's a copy of target attacking legendary creature you control other than Shaun, except it's not legendary and it's a Synth artifact creature in addition to its other types. When Shaun leaves the battlefield, exile all Synth tokens you control.
    }

    private ShaunFatherOfSynths(final ShaunFatherOfSynths card) {
        super(card);
    }

    @Override
    public ShaunFatherOfSynths copy() {
        return new ShaunFatherOfSynths(this);
    }
}
