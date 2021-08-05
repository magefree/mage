package mage.cards.d;

import java.util.UUID;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class DelversTorch extends CardImpl {

    public DelversTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature attacks, venture into the dungeon.
        this.addAbility(new AttacksAttachedTriggeredAbility(new VentureIntoTheDungeonEffect()));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private DelversTorch(final DelversTorch card) {
        super(card);
    }

    @Override
    public DelversTorch copy() {
        return new DelversTorch(this);
    }
}
