

package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class DarksteelPlate extends CardImpl {

    public DarksteelPlate (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Darksteel Plate is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        // Equipped creature is indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield)));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    public DarksteelPlate (final DarksteelPlate card) {
        super(card);
    }

    @Override
    public DarksteelPlate copy() {
        return new DarksteelPlate(this);
    }

}
