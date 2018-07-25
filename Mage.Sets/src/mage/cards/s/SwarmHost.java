package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SwarmHost extends CardImpl {

    public SwarmHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, put a 1/1 green Zerg creature token with haste onto the battlefield. Exile that token at the end of combat.
    }

    public SwarmHost(final SwarmHost card) {
        super(card);
    }

    @Override
    public SwarmHost copy() {
        return new SwarmHost(this);
    }
}
