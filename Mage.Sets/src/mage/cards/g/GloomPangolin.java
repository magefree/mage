package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class GloomPangolin extends CardImpl {

    public GloomPangolin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.PANGOLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);
    }

    private GloomPangolin(final GloomPangolin card) {
        super(card);
    }

    @Override
    public GloomPangolin copy() {
        return new GloomPangolin(this);
    }
}
