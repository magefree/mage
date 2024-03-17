package mage.cards.e;

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
public final class ElderOwynLyons extends CardImpl {

    public ElderOwynLyons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Artifacts you control have ward {1}.
        // When Elder Owyn Lyons enters the battlefield or dies, return target artifact card from your graveyard to your hand.
    }

    private ElderOwynLyons(final ElderOwynLyons card) {
        super(card);
    }

    @Override
    public ElderOwynLyons copy() {
        return new ElderOwynLyons(this);
    }
}
