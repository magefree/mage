package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HippoCows extends CardImpl {

    public HippoCows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HIPPO);
        this.subtype.add(SubType.OX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private HippoCows(final HippoCows card) {
        super(card);
    }

    @Override
    public HippoCows copy() {
        return new HippoCows(this);
    }
}
