
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

/**
 *
 * @author Styxo
 */
public final class TrooperArmor extends CardImpl {

    public TrooperArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equiped creature gets +1/+1 and is a Trooper in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddCardSubtypeAttachedEffect(SubType.TROOPER, AttachmentType.EQUIPMENT)));

        // Whenever a Trooper enters the battlefield under your control, you may attach {this} to it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new AttachEffect(Outcome.BoostCreature, "attach {this} to it"),
                new FilterPermanent(SubType.TROOPER, "Trooper"),
                true,
                SetTargetPointer.PERMANENT
        ));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private TrooperArmor(final TrooperArmor card) {
        super(card);
    }

    @Override
    public TrooperArmor copy() {
        return new TrooperArmor(this);
    }
}
