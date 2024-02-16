package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShipwreckSentry extends CardImpl {

    private static final Hint hint = new ConditionHint(
            ShipwreckSentryWatcher::checkPlayer,
            "An artifact entered under your control this turn"
    );

    public ShipwreckSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as an artifact entered the battlefield under your control this turn, Shipwreck Sentry can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                ShipwreckSentryWatcher::checkPlayer
        ).setText("as long as an artifact entered the battlefield under your control this turn, " +
                "{this} can attack as though it didn't have defender")).addHint(hint), new ShipwreckSentryWatcher());
    }

    private ShipwreckSentry(final ShipwreckSentry card) {
        super(card);
    }

    @Override
    public ShipwreckSentry copy() {
        return new ShipwreckSentry(this);
    }
}

class ShipwreckSentryWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    ShipwreckSentryWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        EntersTheBattlefieldEvent eEvent = (EntersTheBattlefieldEvent) event;
        if (eEvent.getTarget() != null && eEvent.getTarget().isArtifact(game)) {
            playerSet.add(eEvent.getTarget().getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(ShipwreckSentryWatcher.class)
                .playerSet
                .contains(source.getControllerId());
    }
}
