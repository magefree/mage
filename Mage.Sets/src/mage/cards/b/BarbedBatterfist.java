package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbedBatterfist extends CardImpl {

    public BarbedBatterfist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equipped creature gets +1/-1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, -1)));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private BarbedBatterfist(final BarbedBatterfist card) {
        super(card);
    }

    @Override
    public BarbedBatterfist copy() {
        return new BarbedBatterfist(this);
    }
}
