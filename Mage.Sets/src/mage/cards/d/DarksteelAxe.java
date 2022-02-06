

package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
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
public final class DarksteelAxe extends CardImpl {

    public DarksteelAxe (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Indestructible (Effects that say "destroy" don't destroy this artifact.)
        this.addAbility(IndestructibleAbility.getInstance());

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    public DarksteelAxe (final DarksteelAxe card) {
        super(card);
    }

    @Override
    public DarksteelAxe copy() {
        return new DarksteelAxe(this);
    }

}
