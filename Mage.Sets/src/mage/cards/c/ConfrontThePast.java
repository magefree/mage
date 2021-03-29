package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class ConfrontThePast extends CardImpl {

    public ConfrontThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");
        
        this.subtype.add(SubType.LESSON);

        // Choose one —
        // • Return target planeswalker card with mana value X or less from your graveyard to the battlefield.
        // • Remove twice X loyalty counters from target planeswalker an opponent controls.
    }

    private ConfrontThePast(final ConfrontThePast card) {
        super(card);
    }

    @Override
    public ConfrontThePast copy() {
        return new ConfrontThePast(this);
    }
}
