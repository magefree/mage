package mage.cards.h;

import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class HotSoup extends CardImpl {

    public HotSoup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature can't be blocked.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT)));

        // Whenever equipped creature is dealt damage, destroy it.
        this.addAbility(new DealtDamageAttachedTriggeredAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect().setText("destroy it"),
                false, SetTargetPointer.PERMANENT
        ).setTriggerPhrase("Whenever equipped creature is dealt damage, "));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private HotSoup(final HotSoup card) {
        super(card);
    }

    @Override
    public HotSoup copy() {
        return new HotSoup(this);
    }
}
