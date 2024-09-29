package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StaticPrison extends CardImpl {

    public StaticPrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // When Static Prison enters the battlefield, exile target nonland permanent an opponent controls until Static Prison leaves the battlefield. You get {E}{E}.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addEffect(new GetEnergyCountersControllerEffect(2));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);

        // At the beginning of your precombat main phase, sacrifice Static Prison unless you pay {E}.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new PayEnergyCost(1)), TargetController.YOU, false
        ));
    }

    private StaticPrison(final StaticPrison card) {
        super(card);
    }

    @Override
    public StaticPrison copy() {
        return new StaticPrison(this);
    }
}
