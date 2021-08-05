package mage.cards.g;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrizzledOutrider extends CardImpl {

    public GrizzledOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }

    private GrizzledOutrider(final GrizzledOutrider card) {
        super(card);
    }

    @Override
    public GrizzledOutrider copy() {
        return new GrizzledOutrider(this);
    }
}
