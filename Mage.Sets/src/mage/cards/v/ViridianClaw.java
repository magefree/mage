

package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;

/**
 *
 * @author Loki
 */
public final class ViridianClaw extends CardImpl {

    public ViridianClaw (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has first strike")
        );
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), false));
    }

    public ViridianClaw (final ViridianClaw card) {
        super(card);
    }

    @Override
    public ViridianClaw copy() {
        return new ViridianClaw(this);
    }

}
