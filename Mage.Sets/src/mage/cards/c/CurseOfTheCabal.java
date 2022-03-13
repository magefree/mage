package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author anonymous
 */
public final class CurseOfTheCabal extends CardImpl {

    public CurseOfTheCabal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{9}{B}");

        // Target player sacrifices half the permanents they control, rounded down.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CurseOfTheCabalSacrificeEffect());

        // Suspend 2-{2}{B}{B}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl("{2}{B}{B}"), this));

        // At the beginning of each player's upkeep, if Curse of the Cabal is suspended, that player may sacrifice a permanent. If they do, put two time counters on Curse of the Cabal.
        this.addAbility(new CurseOfTheCabalInterveningIfTriggeredAbility());

    }

    private CurseOfTheCabal(final CurseOfTheCabal card) {
        super(card);
    }

    @Override
    public CurseOfTheCabal copy() {
        return new CurseOfTheCabal(this);
    }
}

class CurseOfTheCabalSacrificeEffect extends OneShotEffect {

    public CurseOfTheCabalSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices half the permanents they control, rounded down.";
    }

    public CurseOfTheCabalSacrificeEffect(final CurseOfTheCabalSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfTheCabalSacrificeEffect copy() {
        return new CurseOfTheCabalSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            int amount = game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_PERMANENT, targetPlayer.getId(), game) / 2;
            if (amount < 1) {
                return true;
            }
            Target target = new TargetControlledPermanent(amount, amount, StaticFilters.FILTER_CONTROLLED_PERMANENT, true);
            if (target.canChoose(targetPlayer.getId(), source, game)) {
                while (!target.isChosen() 
                        && target.canChoose(targetPlayer.getId(), source, game) && targetPlayer.canRespond()) {
                    targetPlayer.choose(Outcome.Sacrifice, target, source, game);
                }
                //sacrifice all chosen (non null) permanents
                target.getTargets().stream()
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .forEach(permanent -> permanent.sacrifice(source, game));
            }
            return true;
        }
        return false;
    }
}

class CurseOfTheCabalInterveningIfTriggeredAbility extends ConditionalInterveningIfTriggeredAbility {

    public CurseOfTheCabalInterveningIfTriggeredAbility() {
        super(new BeginningOfUpkeepTriggeredAbility(
                Zone.EXILED, new CurseOfTheCabalTriggeredAbilityConditionalDelay(),
                TargetController.ANY, false, true
        ),
                SuspendedCondition.instance,
                "At the beginning of each player's upkeep, if {this} is suspended, "
                        + "that player may sacrifice a permanent. If they do, "
                        + "put two time counters on {this}."
        );
        // controller has to sac a permanent
        // counters aren't placed
    }

    public CurseOfTheCabalInterveningIfTriggeredAbility(final CurseOfTheCabalInterveningIfTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public CurseOfTheCabalInterveningIfTriggeredAbility copy() {
        return new CurseOfTheCabalInterveningIfTriggeredAbility(this);
    }
}

class CurseOfTheCabalTriggeredAbilityConditionalDelay extends AddCountersSourceEffect {

    public CurseOfTheCabalTriggeredAbilityConditionalDelay() {
        super(CounterType.TIME.createInstance(), StaticValue.get(2), false, true);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID activePlayerId = game.getActivePlayerId();
        Player target = game.getPlayer(activePlayerId);
        Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledPermanent()));
        if (target == null) {
            return false;
        }
        if (cost.canPay(source, source, activePlayerId, game)
                && target.chooseUse(Outcome.Sacrifice, "Sacrifice a permanent to delay Curse of the Cabal?", source, game)
                && cost.pay(source, game, source, activePlayerId, true, null)) {
            return super.apply(game, source);
        }
        return true;
    }

    public CurseOfTheCabalTriggeredAbilityConditionalDelay(final CurseOfTheCabalTriggeredAbilityConditionalDelay effect) {
        super(effect);
    }

    @Override
    public CurseOfTheCabalTriggeredAbilityConditionalDelay copy() {
        return new CurseOfTheCabalTriggeredAbilityConditionalDelay(this);
    }
}
