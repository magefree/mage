package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author gp66
 */
public final class EssenceSymbiote extends CardImpl {

    public EssenceSymbiote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control mutates, put a +1/+1 counter on that creature and you gain 2 life.
    }

    private EssenceSymbiote(final EssenceSymbiote card) {
        super(card);
    }

    @Override
    public EssenceSymbiote copy() {
        return new EssenceSymbiote(this);
    }
}
