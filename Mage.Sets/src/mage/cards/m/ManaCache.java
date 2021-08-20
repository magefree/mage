package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CountersSourceCount;

/**
 * @author L_J
 */
public final class ManaCache extends CardImpl {

    public ManaCache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // At the beginning of each player's end step, put a charge counter on Mana Cache for each untapped land that player controls.
        TriggeredAbility ability = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of each player's end step", true, new ManaCacheEffect());
        this.addAbility(ability);

        // Remove a charge counter from Mana Cache: Add {C}. Any player may activate this ability but only during their turn before the end step.
        this.addAbility(new ManaCacheManaAbility());
    }

    private ManaCache(final ManaCache card) {
        super(card);
    }

    @Override
    public ManaCache copy() {
        return new ManaCache(this);
    }
}

class ManaCacheEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ManaCacheEffect() {
        super(Outcome.Damage);
        this.staticText = "put a charge counter on {this} for each untapped land that player controls";
    }

    private ManaCacheEffect(final ManaCacheEffect effect) {
        super(effect);
    }

    @Override
    public ManaCacheEffect copy() {
        return new ManaCacheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int controlledUntappedLands = game.getBattlefield().countAll(filter, game.getActivePlayerId(), game);
            sourcePermanent.addCounters(CounterType.CHARGE.createInstance(controlledUntappedLands), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}

class ManaCacheManaAbility extends ActivatedManaAbilityImpl {

    public ManaCacheManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.ColorlessMana(1), new CountersSourceCount(CounterType.CHARGE)),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 0, 1));
        this.setMayActivate(TargetController.ANY);
    }

    public ManaCacheManaAbility(final ManaCacheManaAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // any player, but only during their turn before the end step
        Player player = game.getPlayer(playerId);
        if (player == null
                || !playerId.equals(game.getActivePlayerId())
                || !game.getStep().getType().isBefore(PhaseStep.END_TURN)) {
            return ActivationStatus.getFalse();
        }

        return super.canActivate(playerId, game);
    }

    @Override
    public ManaCacheManaAbility copy() {
        return new ManaCacheManaAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Any player may activate this ability but only during their turn before the end step.";
    }
}
