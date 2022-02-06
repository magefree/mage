
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnattachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeEquippedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author nantuko
 */
public final class GraftedExoskeleton extends CardImpl {

    public GraftedExoskeleton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);
        
        // Equipped creature gets +2/+2 and has infect.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(InfectAbility.getInstance(), AttachmentType.EQUIPMENT)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));

        // Whenever Grafted Exoskeleton becomes unattached from a permanent, sacrifice that permanent.
        this.addAbility(new UnattachedTriggeredAbility(new SacrificeEquippedEffect(), false));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private GraftedExoskeleton(final GraftedExoskeleton card) {
        super(card);
    }

    @Override
    public GraftedExoskeleton copy() {
        return new GraftedExoskeleton(this);
    }
}
