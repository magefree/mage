
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ChitinousCloak extends CardImpl {

    public ChitinousCloak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has menace.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(new MenaceAbility(), AttachmentType.EQUIPMENT);
        effect.setText("and has menace. <i>(It can't be blocked except by two or more creatures.)</i>");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private ChitinousCloak(final ChitinousCloak card) {
        super(card);
    }

    @Override
    public ChitinousCloak copy() {
        return new ChitinousCloak(this);
    }
}
