package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodthornFlail extends CardImpl {

    public BloodthornFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip--Pay {3} or discard a card.
        this.addAbility(new EquipAbility(
                Outcome.BoostCreature,
                new OrCost(
                        "Pay {3} or discard a card",
                        new GenericManaCost(3), new DiscardCardCost()
                ), false));
    }

    private BloodthornFlail(final BloodthornFlail card) {
        super(card);
    }

    @Override
    public BloodthornFlail copy() {
        return new BloodthornFlail(this);
    }
}
