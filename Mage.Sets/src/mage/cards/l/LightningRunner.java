
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class LightningRunner extends CardImpl {

    public LightningRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Lightning Runner attacks, you get {E}{E}, then you may pay {E}{E}{E}{E}{E}{E}{E}{E}. If you do,
        // untap all creatures you control and after this phase, there is an additional combat phase.
        this.addAbility(new AttacksTriggeredAbility(new LightningRunnerEffect(), false));
    }

    private LightningRunner(final LightningRunner card) {
        super(card);
    }

    @Override
    public LightningRunner copy() {
        return new LightningRunner(this);
    }
}

class LightningRunnerEffect extends OneShotEffect {

    LightningRunnerEffect() {
        super(Outcome.Benefit);
        staticText = "you get {E}{E}, then you may pay {E}{E}{E}{E}{E}{E}{E}{E}. If you pay, "
                + "untap all creatures you control, and after this phase, there is an additional combat phase";
    }

    LightningRunnerEffect(final LightningRunnerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new GetEnergyCountersControllerEffect(2).apply(game, source);
            if (controller.getCounters().getCount(CounterType.ENERGY) > 7) {
                Cost cost = new PayEnergyCost(8);
                if (controller.chooseUse(outcome,
                        "Pay {E}{E}{E}{E}{E}{E}{E}{E} to use this? ",
                        "Untap all creatures you control and after this phase, there is an additional combat phase.",
                        "Yes", "No", source, game)
                        && cost.pay(source, game, source, source.getControllerId(), true)) {
                    new UntapAllControllerEffect(new FilterControlledCreaturePermanent()).apply(game, source);
                    new AdditionalCombatPhaseEffect().apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LightningRunnerEffect copy() {
        return new LightningRunnerEffect(this);
    }

}
