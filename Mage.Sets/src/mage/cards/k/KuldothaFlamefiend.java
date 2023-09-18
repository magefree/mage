package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author North
 */
public final class KuldothaFlamefiend extends CardImpl {

    public KuldothaFlamefiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Kuldotha Flamefiend enters the battlefield, you may sacrifice an artifact. If you do, Kuldotha Flamefiend deals 4 damage divided as you choose among any number of targets.
        EntersBattlefieldTriggeredAbility ability = 
                new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new DamageMultiEffect(4), new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN))), false);
        ability.addTarget(new TargetAnyTargetAmount(4));
        this.addAbility(ability);
    }

    private KuldothaFlamefiend(final KuldothaFlamefiend card) {
        super(card);
    }

    @Override
    public KuldothaFlamefiend copy() {
        return new KuldothaFlamefiend(this);
    }
}
