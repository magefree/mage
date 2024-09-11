package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ManifestDreadThenAttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConductiveMachete extends CardImpl {

    public ConductiveMachete(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Conductive Machete enters, manifest dread, then attach Conductive Machete to that creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadThenAttachEffect()));

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private ConductiveMachete(final ConductiveMachete card) {
        super(card);
    }

    @Override
    public ConductiveMachete copy() {
        return new ConductiveMachete(this);
    }
}
