
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author fireshoes
 */
public final class BladeOfSelves extends CardImpl {

    public BladeOfSelves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has myriad.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(new MyriadAbility(), AttachmentType.EQUIPMENT)));
        
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), false));
    }

    private BladeOfSelves(final BladeOfSelves card) {
        super(card);
    }

    @Override
    public BladeOfSelves copy() {
        return new BladeOfSelves(this);
    }
}
