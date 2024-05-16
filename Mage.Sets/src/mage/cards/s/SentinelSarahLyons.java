package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.BattalionAbility;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;
import mage.watchers.Watcher;

/**
 * @author Cguy7777
 */
public final class SentinelSarahLyons extends CardImpl {

    private static final Hint hint = new ConditionHint(
            SentinelSarahLyonsWatcher::checkPlayer,
            "An artifact entered under your control this turn");

    public SentinelSarahLyons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as an artifact entered the battlefield under your control this turn, creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield),
                SentinelSarahLyonsWatcher::checkPlayer,
                "as long as an artifact entered the battlefield under your control this turn, " +
                        "creatures you control get +2/+2")).addHint(hint), new SentinelSarahLyonsWatcher());

        // Battalion -- Whenever Sentinel Sarah Lyons and at least two other creatures attack,
        // Sentinel Sarah Lyons deals damage equal to the number of artifacts you control to target player.
        Ability ability = new BattalionAbility(new DamageTargetEffect(ArtifactYouControlCount.instance)
                .setText("{this} deals damage equal to the number of artifacts you control to target player"));
        ability.addTarget(new TargetPlayer());
        ability.addHint(ArtifactYouControlHint.instance);
        this.addAbility(ability);
    }

    private SentinelSarahLyons(final SentinelSarahLyons card) {
        super(card);
    }

    @Override
    public SentinelSarahLyons copy() {
        return new SentinelSarahLyons(this);
    }
}

// Copied from ShipwreckSentryWatcher
class SentinelSarahLyonsWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    SentinelSarahLyonsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        EntersTheBattlefieldEvent eEvent = (EntersTheBattlefieldEvent) event;
        if (eEvent.getTarget() != null && eEvent.getTarget().isArtifact(game)) {
            playerSet.add(eEvent.getTarget().getControllerId());
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
                .getWatcher(SentinelSarahLyonsWatcher.class)
                .playerSet
                .contains(source.getControllerId());
    }
}
