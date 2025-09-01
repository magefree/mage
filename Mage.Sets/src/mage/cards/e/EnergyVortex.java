package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnergyVortex extends CardImpl {

    public EnergyVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // As Energy Vortex enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));

        // At the beginning of your upkeep, remove all vortex counters from Energy Vortex.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RemoveAllCountersSourceEffect(CounterType.VORTEX)
                        .setText("remove all vortex counters from {this}")
        ));

        // At the beginning of the chosen player's upkeep, Energy Vortex deals 3 damage to that player unless they pay {1} for each vortex counter on Energy Vortex.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new EnergyVortexEffect(), false
        ).withTriggerCondition(EnergyVortexCondition.instance)
                .setTriggerPhrase("At the beginning of the chosen player's upkeep, "));

        // {X}: Put X vortex counters on Energy Vortex. Activate this ability only during your upkeep.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new AddCountersSourceEffect(
                        CounterType.VORTEX.createInstance(),
                        GetXValue.instance, true
                ), new ManaCostsImpl<>("{X}"),
                IsStepCondition.getMyUpkeep()
        ));
    }

    private EnergyVortex(final EnergyVortex card) {
        super(card);
    }

    @Override
    public EnergyVortex copy() {
        return new EnergyVortex(this);
    }
}

enum EnergyVortexCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getActivePlayerId().equals(game.getState().getValue(source.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY));
    }

    @Override
    public String toString() {
        return "";
    }
}

class EnergyVortexEffect extends OneShotEffect {

    EnergyVortexEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to that player unless they pay {1} for each vortex counter on {this}";
    }

    private EnergyVortexEffect(final EnergyVortexEffect effect) {
        super(effect);
    }

    @Override
    public EnergyVortexEffect copy() {
        return new EnergyVortexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        int counters = permanent.getCounters(game).getCount(CounterType.VORTEX);
        Cost cost = ManaUtil.createManaCost(counters, false);
        return cost.pay(source, game, source, player.getId(), false)
                || player.damage(3, source.getSourceId(), source, game) > 0;
    }
}
