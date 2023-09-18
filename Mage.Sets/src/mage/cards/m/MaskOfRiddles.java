
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class MaskOfRiddles extends CardImpl {

    public MaskOfRiddles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{U}{B}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has fear.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FearAbility.getInstance(), AttachmentType.EQUIPMENT)));
        // Whenever equipped creature deals combat damage to a player, you may draw a card.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new DrawCardSourceControllerEffect(1), "equipped", true));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private MaskOfRiddles(final MaskOfRiddles card) {
        super(card);
    }

    @Override
    public MaskOfRiddles copy() {
        return new MaskOfRiddles(this);
    }
}
