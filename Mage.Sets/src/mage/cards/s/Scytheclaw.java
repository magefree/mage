
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
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
 * @author fireshoes
 */
public final class Scytheclaw extends CardImpl {

    public Scytheclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature deals combat damage to a player, that player loses half of their life, round up.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new LoseHalfLifeTargetEffect(), "equipped creature", false, true));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), false));
    }

    private Scytheclaw(final Scytheclaw card) {
        super(card);
    }

    @Override
    public Scytheclaw copy() {
        return new Scytheclaw(this);
    }
}
