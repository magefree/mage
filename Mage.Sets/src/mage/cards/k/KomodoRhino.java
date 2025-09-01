package mage.cards.k;

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
public final class KomodoRhino extends CardImpl {

    public KomodoRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private KomodoRhino(final KomodoRhino card) {
        super(card);
    }

    @Override
    public KomodoRhino copy() {
        return new KomodoRhino(this);
    }
}
