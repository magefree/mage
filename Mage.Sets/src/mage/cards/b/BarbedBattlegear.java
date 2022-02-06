

package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class BarbedBattlegear extends CardImpl {

    public BarbedBattlegear (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(4, -1)));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    public BarbedBattlegear (final BarbedBattlegear card) {
        super(card);
    }

    @Override
    public BarbedBattlegear copy() {
        return new BarbedBattlegear(this);
    }

}
