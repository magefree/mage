
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ForgottenHarvest extends CardImpl {

    public ForgottenHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // At the beginning of your upkeep, you may exile a land card from your graveyard. If you do, put a +1/+1 counter on target creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterLandCard("land card from your graveyard")))
                ),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ForgottenHarvest(final ForgottenHarvest card) {
        super(card);
    }

    @Override
    public ForgottenHarvest copy() {
        return new ForgottenHarvest(this);
    }
}
