
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ShroudAbility;
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
public final class LightningGreaves extends CardImpl {

    public LightningGreaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has haste and shroud.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT));
        Effect effect = new GainAbilityAttachedEffect(ShroudAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and shroud");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Equip {0}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(0), false));
    }

    private LightningGreaves(final LightningGreaves card) {
        super(card);
    }

    @Override
    public LightningGreaves copy() {
        return new LightningGreaves(this);
    }
}
