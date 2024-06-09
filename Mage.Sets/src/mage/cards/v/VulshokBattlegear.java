

package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
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
 * @author Loki
 */
public final class VulshokBattlegear extends CardImpl {

    public VulshokBattlegear (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 3)));
    }

    private VulshokBattlegear(final VulshokBattlegear card) {
        super(card);
    }

    @Override
    public VulshokBattlegear copy() {
        return new VulshokBattlegear(this);
    }

}
