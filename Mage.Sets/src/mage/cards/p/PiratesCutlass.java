
package mage.cards.p;

import mage.abilities.common.EntersBattlefieldAttachToTarget;
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
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiratesCutlass extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PIRATE);

    public PiratesCutlass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Pirate's Cutlass enters the battlefield, attach it to target Pirate you control.
        this.addAbility(new EntersBattlefieldAttachToTarget(filter));

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 1)));

        // Equip 2
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));

    }

    private PiratesCutlass(final PiratesCutlass card) {
        super(card);
    }

    @Override
    public PiratesCutlass copy() {
        return new PiratesCutlass(this);
    }
}
