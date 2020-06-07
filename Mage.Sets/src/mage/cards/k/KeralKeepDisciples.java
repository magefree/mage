package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class KeralKeepDisciples extends CardImpl {

    public KeralKeepDisciples(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you activate a loyalty ability of a Chandra planeswalker, Keral Keep Disciples deals 1 damage to each opponent.
    }

    private KeralKeepDisciples(final KeralKeepDisciples card) {
        super(card);
    }

    @Override
    public KeralKeepDisciples copy() {
        return new KeralKeepDisciples(this);
    }
}
