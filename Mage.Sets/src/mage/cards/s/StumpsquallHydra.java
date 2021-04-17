package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetAmount;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StumpsquallHydra extends CardImpl {

    public StumpsquallHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Stumpsquall Hydra enters the battlefield, distribute X +1/+1 counters among it and any number of commanders.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StumpsquallHydraEffect()), new StumpsquallHydraWatcher());
    }

    private StumpsquallHydra(final StumpsquallHydra card) {
        super(card);
    }

    @Override
    public StumpsquallHydra copy() {
        return new StumpsquallHydra(this);
    }
}

class StumpsquallHydraEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterPermanent("this permanent or any number of commanders");

    static {
        filter.add(StumpsquallHydraPredicate.instance);
    }

    private enum StumpsquallHydraPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<MageObject>> {
        instance;

        @Override
        public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
            return input.getObject().getId().equals(input.getSourceId())
                    || CommanderPredicate.instance.apply(input.getObject(), game);
        }

        @Override
        public String toString() {
            return "";
        }
    }

    StumpsquallHydraEffect() {
        super(Outcome.Benefit);
        staticText = "distribute X +1/+1 counters among it and any number of commanders";
    }

    private StumpsquallHydraEffect(final StumpsquallHydraEffect effect) {
        super(effect);
    }

    @Override
    public StumpsquallHydraEffect copy() {
        return new StumpsquallHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StumpsquallHydraWatcher watcher = game.getState().getWatcher(StumpsquallHydraWatcher.class, source.getSourceId());
        if (watcher == null || game.getState().getValue(source.getSourceId().toString() + "xValue") == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        int xValue = (Integer) game.getState().getValue(source.getSourceId().toString() + "xValue");
        if (player == null || xValue < 1) {
            return false;
        }
        TargetAmount targetAmount = new TargetCreatureOrPlaneswalkerAmount(xValue, filter);
        targetAmount.setNotTarget(true);
        player.choose(outcome, targetAmount, source.getSourceId(), game);
        for (UUID targetId : targetAmount.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(targetAmount.getTargetAmount(targetId)), source.getControllerId(), source, game);
        }
        return true;
    }

}

class StumpsquallHydraWatcher extends Watcher {

    StumpsquallHydraWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpellOrLKIStack(event.getTargetId());
        if (spell == null) {
            return;
        }
        if (spell.getSourceId() != super.getSourceId()) {
            return;
        }
        game.getState().setValue(spell.getSourceId().toString() + "xValue", spell.getSpellAbility().getManaCostsToPay().getX());
    }
}
