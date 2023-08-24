package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ProvisionsMerchant extends CardImpl {

    public ProvisionsMerchant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Provisions Merchant enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever Provisions Merchant attacks, you may sacrifice a Food. If you do, attacking creatures get +1/+1 and gain trample until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostAllEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false)
                        .setText("attacking creatures get +1/+1"),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD)
        ).addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES,
                "and gain trample until end of turn"))));
    }

    private ProvisionsMerchant(final ProvisionsMerchant card) {
        super(card);
    }

    @Override
    public ProvisionsMerchant copy() {
        return new ProvisionsMerchant(this);
    }
}
