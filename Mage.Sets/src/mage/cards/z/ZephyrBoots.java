package mage.cards.z;

import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
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
public final class ZephyrBoots extends CardImpl {

    public ZephyrBoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Whenever equipped creature deals combat damage to a player, draw a card, then discard a card.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1),
                "equipped creature", false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ZephyrBoots(final ZephyrBoots card) {
        super(card);
    }

    @Override
    public ZephyrBoots copy() {
        return new ZephyrBoots(this);
    }
}
