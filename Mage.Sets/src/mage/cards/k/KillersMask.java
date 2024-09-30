package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ManifestDreadThenAttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KillersMask extends CardImpl {

    public KillersMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Killer's Mask enters, manifest dread, then attach Killer's Mask to that creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadThenAttachEffect()));

        // Equipped creature has menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new MenaceAbility(false), AttachmentType.EQUIPMENT
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private KillersMask(final KillersMask card) {
        super(card);
    }

    @Override
    public KillersMask copy() {
        return new KillersMask(this);
    }
}
