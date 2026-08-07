package mage.cards.r;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RaggedShortSpear extends CardImpl {

    public RaggedShortSpear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, you may discard a card. If you do, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost())
        ));

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private RaggedShortSpear(final RaggedShortSpear card) {
        super(card);
    }

    @Override
    public RaggedShortSpear copy() {
        return new RaggedShortSpear(this);
    }
}
