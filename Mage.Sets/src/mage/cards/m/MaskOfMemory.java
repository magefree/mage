
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;

/**
 *
 * @author Quercitron
 */
public final class MaskOfMemory extends CardImpl {

    public MaskOfMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, you may draw two cards. If you do, discard a card.
        Ability ability = new DealsDamageToAPlayerAttachedTriggeredAbility(new DrawCardSourceControllerEffect(2), "equipped creature", true);
        Effect effect = new DiscardControllerEffect(1);
        effect.setText("If you do, discard a card");
        ability.addEffect(effect);
        this.addAbility(ability);
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private MaskOfMemory(final MaskOfMemory card) {
        super(card);
    }

    @Override
    public MaskOfMemory copy() {
        return new MaskOfMemory(this);
    }
}
