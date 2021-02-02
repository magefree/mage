
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;


/**
 *
 * @author Pete Rossi
 */
public final class SpectersShroud extends CardImpl {

    public SpectersShroud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        // Whenever equipped creature deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new DiscardTargetEffect(1), "equipped creature", false, true, true));
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private SpectersShroud(final SpectersShroud card) {
        super(card);
    }

    @Override
    public SpectersShroud copy() {
        return new SpectersShroud(this);
    }
}
