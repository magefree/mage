

package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 * @author Loki
 */
public final class Batterskull extends CardImpl {

    public Batterskull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon (When this Equipment enters the battlefield, create a 0/0 black Germ creature token, then attach this to it.)
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +4/+4 and has vigilance and lifelink.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(4, 4)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // {3}: Return Batterskull to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new GenericManaCost(3)));

        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(5), false));
    }

    private Batterskull(final Batterskull card) {
        super(card);
    }

    @Override
    public Batterskull copy() {
        return new Batterskull(this);
    }

}
