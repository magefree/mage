
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author North, Loki
 */
public final class SylvokLifestaff extends CardImpl {

    public SylvokLifestaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        // Whenever equipped creature dies, you gain 3 life.
        this.addAbility(new DiesAttachedTriggeredAbility(new GainLifeEffect(3), "equipped creature"));
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), false));
    }

    private SylvokLifestaff(final SylvokLifestaff card) {
        super(card);
    }

    @Override
    public SylvokLifestaff copy() {
        return new SylvokLifestaff(this);
    }
}
