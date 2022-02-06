
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class Skinwing extends CardImpl {

    public Skinwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living Weapom
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +2/+2 and has flying
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Equip {6}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(6), false));
    }

    private Skinwing(final Skinwing card) {
        super(card);
    }

    @Override
    public Skinwing copy() {
        return new Skinwing(this);
    }
}
