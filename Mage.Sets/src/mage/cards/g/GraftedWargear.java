
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnattachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeTargetEffect;
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
 * @author LevelX2
 */
public final class GraftedWargear extends CardImpl {

    public GraftedWargear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 2)));

        // Whenever Grafted Wargear becomes unattached from a permanent, sacrifice that permanent.
        this.addAbility(new UnattachedTriggeredAbility(new SacrificeTargetEffect().setText("sacrifice that permanent"), false));

        // Equip {0}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(0)));

    }

    private GraftedWargear(final GraftedWargear card) {
        super(card);
    }

    @Override
    public GraftedWargear copy() {
        return new GraftedWargear(this);
    }
}
