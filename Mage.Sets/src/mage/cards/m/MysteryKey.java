package mage.cards.m;

import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysteryKey extends CardImpl {

    public MysteryKey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When equipped creature deals combat damage to a player, sacrifice Mystery Key. If you do, draw three cards.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(3),
                        new SacrificeSourceCost(), null, false
                ), "equipped", false
        ).setTriggerPhrase("When equipped creature deals combat damage to a player, "));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private MysteryKey(final MysteryKey card) {
        super(card);
    }

    @Override
    public MysteryKey copy() {
        return new MysteryKey(this);
    }
}
