

package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class Sickleslicer extends CardImpl {

    public Sickleslicer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon (When this Equipment enters the battlefield, create a 0/0 black Phyrexian Germ creature token, then attach this to it.)
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), false));
    }

    public Sickleslicer (final Sickleslicer card) {
        super(card);
    }

    @Override
    public Sickleslicer copy() {
        return new Sickleslicer(this);
    }

}
