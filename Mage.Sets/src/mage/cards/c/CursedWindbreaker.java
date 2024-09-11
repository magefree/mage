package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ManifestDreadThenAttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CursedWindbreaker extends CardImpl {

    public CursedWindbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Cursed Windbreaker enters, manifest dread, then attach Cursed Windbreaker to that creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadThenAttachEffect()));

        // Equipped creature has flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private CursedWindbreaker(final CursedWindbreaker card) {
        super(card);
    }

    @Override
    public CursedWindbreaker copy() {
        return new CursedWindbreaker(this);
    }
}
