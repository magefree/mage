package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StingBilbosSword extends CardImpl {

    public StingBilbosSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Sting enters, put a hone counter on Sting for each creature target opponent controls. Attach Sting to up to one target creature you control.

        // Equip {3}
        this.addAbility(new EquipAbility(3));

    }

    private StingBilbosSword(final StingBilbosSword card) {
        super(card);
    }

    @Override
    public StingBilbosSword copy() {
        return new StingBilbosSword(this);
    }
}
