package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DragonToken;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RithLiberatedPrimeval extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragons");

    public RithLiberatedPrimeval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Other Dragons you control have ward {2}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new ManaCostsImpl<>("{2}"), false),
                Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of your end step, if a creature or planeswalker an opponent controlled was dealt excess damage this turn, create a 4/4 red Dragon creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new DragonToken()), TargetController.YOU,
                RithLiberatedPrimevalCondition.instance, false
        ), new RithLiberatedPrimevalWatcher());
    }

    private RithLiberatedPrimeval(final RithLiberatedPrimeval card) {
        super(card);
    }

    @Override
    public RithLiberatedPrimeval copy() {
        return new RithLiberatedPrimeval(this);
    }
}

enum RithLiberatedPrimevalCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return RithLiberatedPrimevalWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a creature or planeswalker an opponent controlled was dealt excess damage this turn";
    }
}

class RithLiberatedPrimevalWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    RithLiberatedPrimevalWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                && ((DamagedEvent) event).getExcess() > 0) {
            playerSet.addAll(game.getOpponents(game.getControllerId(event.getTargetId())));
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(RithLiberatedPrimevalWatcher.class)
                .playerSet
                .contains(playerId);
    }
}
