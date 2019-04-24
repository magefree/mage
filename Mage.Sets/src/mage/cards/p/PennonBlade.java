
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class PennonBlade extends CardImpl {

    public PennonBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(value, value)));
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4)));
    }

    public PennonBlade(final PennonBlade card) {
        super(card);
    }

    @Override
    public PennonBlade copy() {
        return new PennonBlade(this);
    }
}
