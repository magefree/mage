package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class SanctumOfTranquilLight extends CardImpl {

    public SanctumOfTranquilLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // {5}{W}: Tap target creature. This ability costs {1} less to activate for each Shrine you control.
    }

    private SanctumOfTranquilLight(final SanctumOfTranquilLight card) {
        super(card);
    }

    @Override
    public SanctumOfTranquilLight copy() {
        return new SanctumOfTranquilLight(this);
    }
}
