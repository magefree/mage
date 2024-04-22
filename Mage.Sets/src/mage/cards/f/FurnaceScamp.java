package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class FurnaceScamp extends CardImpl {

    public FurnaceScamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Furnace Scamp deals combat damage to a player, you may sacrifice it. If you do, Furnace Scamp deals 3 damage to that player.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new DamageTargetEffect(3, true, "that player"), new SacrificeSourceCost()
        ), false, true);
        this.addAbility(ability);
    }

    private FurnaceScamp(final FurnaceScamp card) {
        super(card);
    }

    @Override
    public FurnaceScamp copy() {
        return new FurnaceScamp(this);
    }
}
