
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnattachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyEquippedEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class CaptainsHook extends CardImpl {

    public CaptainsHook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0
        Effect effect = new BoostEquippedEffect(2, 0);
        effect.setText("Equipped creature gets +2/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        // has menace,
        effect = new GainAbilityAttachedEffect(new MenaceAbility(), AttachmentType.EQUIPMENT);
        effect.setText(", has menace");
        ability.addEffect(effect);
        //, and is a Pirate in addition to its other creature types
        effect = new AddCardSubtypeAttachedEffect(SubType.PIRATE, Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT);
        effect.setText(", and is a Pirate in addition to its other creature types");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Whenever Captain's Hook becomes unattached from a permanent, destroy that permanent.
        this.addAbility(new UnattachedTriggeredAbility(new DestroyEquippedEffect(), false));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), false));
    }

    private CaptainsHook(final CaptainsHook card) {
        super(card);
    }

    @Override
    public CaptainsHook copy() {
        return new CaptainsHook(this);
    }
}
