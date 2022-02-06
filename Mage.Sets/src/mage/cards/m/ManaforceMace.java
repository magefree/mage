
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class ManaforceMace extends CardImpl {

    public ManaforceMace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Domain - Equipped creature gets +1/+1 for each basic land type among lands you control.
        DomainValue value = new DomainValue();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(value, value)).addHint(DomainHint.instance));
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private ManaforceMace(final ManaforceMace card) {
        super(card);
    }

    @Override
    public ManaforceMace copy() {
        return new ManaforceMace(this);
    }
}
