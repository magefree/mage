package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RobotToken;

/**
 * @author Cguy7777
 */
public final class AutomatedAssemblyLine extends CardImpl {

    public AutomatedAssemblyLine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // Whenever one or more artifact creatures you control deal combat damage to a player, you get {E}.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new GetEnergyCountersControllerEffect(1), StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE));

        // Pay {E}{E}{E}: Create a tapped 3/3 colorless Robot artifact creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new RobotToken(), 1, true),
                new PayEnergyCost(3)));
    }

    private AutomatedAssemblyLine(final AutomatedAssemblyLine card) {
        super(card);
    }

    @Override
    public AutomatedAssemblyLine copy() {
        return new AutomatedAssemblyLine(this);
    }
}
