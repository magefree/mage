
package mage.cards.t;

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
public final class TrustyMachete extends CardImpl {

    public TrustyMachete(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 1)));
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private TrustyMachete(final TrustyMachete card) {
        super(card);
    }

    @Override
    public TrustyMachete copy() {
        return new TrustyMachete(this);
    }

}
