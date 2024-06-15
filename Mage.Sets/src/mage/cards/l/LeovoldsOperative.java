package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author tiera3 - based on PrizefighterConstruct
 * note - draftmatters ability not implemented
 */
public final class LeovoldsOperative extends CardImpl {

    public LeovoldsOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private LeovoldsOperative(final LeovoldsOperative card) {
        super(card);
    }

    @Override
    public LeovoldsOperative copy() {
        return new LeovoldsOperative(this);
    }
}
