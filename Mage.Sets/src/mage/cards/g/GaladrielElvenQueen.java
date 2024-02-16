package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author Susucr
 */
public final class GaladrielElvenQueen extends CardImpl {

    public GaladrielElvenQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Will of the council -- At the beginning of combat on your turn, if another Elf entered the battlefield under your control this turn, starting with you, each player votes for dominion or guidance. If dominion gets more votes, the Ring tempts you, then you put a +1/+1 counter on your Ring-bearer. If guidance gets more votes or the vote is tied, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        Zone.BATTLEFIELD,
                        new GaladrielElvenQueenEffect(),
                        TargetController.YOU, false, false
                ),
                GaladrielElvenQueenCondition.instance,
                "At the beginning of combat on your turn, if another Elf entered the battlefield under "
                        + "your control this turn, starting with you, each player votes for dominion or guidance. "
                        + "If dominion gets more votes, the Ring tempts you, then you put a +1/+1 counter on your "
                        + "Ring-bearer. If guidance gets more votes or the vote is tied, draw a card."
        ).setAbilityWord(AbilityWord.WILL_OF_THE_COUNCIL), new GaladrielElvenQueenWatcher());
    }

    private GaladrielElvenQueen(final GaladrielElvenQueen card) {
        super(card);
    }

    @Override
    public GaladrielElvenQueen copy() {
        return new GaladrielElvenQueen(this);
    }
}


enum GaladrielElvenQueenCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        GaladrielElvenQueenWatcher watcher = game.getState().getWatcher(GaladrielElvenQueenWatcher.class);
        return watcher != null && watcher
                .hasPlayerHadAnotherElfEnterThisTurn(
                        game,
                        source.getSourcePermanentOrLKI(game),
                        source.getControllerId()
                );
    }
}

class GaladrielElvenQueenWatcher extends Watcher {

    // Map players UUID to theirs elves MageObjectReference that entered this turn.
    private final Map<UUID, Set<MageObjectReference>> elfEnterings = new HashMap<>();

    GaladrielElvenQueenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && !game.isSimulation()) {

            // Is the thing entering an elf?
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null || !permanent.hasSubtype(SubType.ELF, game)) {
                return;
            }

            // Then, stores the info about that event for latter.
            UUID playerId = event.getPlayerId();
            Set<MageObjectReference> setForThatPlayer = this.elfEnterings.getOrDefault(playerId, new HashSet<>());

            MageObjectReference elfMOR = new MageObjectReference(permanent.getId(), game);
            setForThatPlayer.add(elfMOR);
            this.elfEnterings.put(playerId, setForThatPlayer);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.elfEnterings.clear();
    }

    boolean hasPlayerHadAnotherElfEnterThisTurn(Game game, Permanent sourcePermanent, UUID playerId) {
        // The trick there is that we need to exclude the sourcePermanent of the trigger (Galadriel most likely),
        //     from the elves that entered the battlefield this turn under the player.

        // we do use MageObjectReference for when Galadriel is entering the battlefield
        //    multiple time in the same turn (flickered for instance)
        MageObjectReference sourceMOR = sourcePermanent == null ? null
                : new MageObjectReference(sourcePermanent.getId(), game);

        Set<MageObjectReference> setForThePlayer = this.elfEnterings.getOrDefault(playerId, new HashSet<>());
        return setForThePlayer.stream().anyMatch(
                elfMOR -> !(elfMOR.equals(sourceMOR))
        );
    }
}

class GaladrielElvenQueenEffect extends OneShotEffect {

    GaladrielElvenQueenEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for dominion or guidance. "
                + "If dominion gets more votes, the Ring tempts you, then you put a +1/+1 counter on your "
                + "Ring-bearer. If guidance gets more votes or the vote is tied, draw a card.";
    }

    private GaladrielElvenQueenEffect(final GaladrielElvenQueenEffect effect) {
        super(effect);
    }

    @Override
    public GaladrielElvenQueenEffect copy() {
        return new GaladrielElvenQueenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TwoChoiceVote vote = new TwoChoiceVote(
                "Dominion",
                "Guidance",
                Outcome.Benefit);

        // starting with you, each player votes for dominion or guidance.
        vote.doVotes(source, game);

        int dominionCount = vote.getVoteCount(true);
        int guidanceCount = vote.getVoteCount(false);

        if (dominionCount > guidanceCount) {
            // If dominion gets more votes, the Ring tempts you,
            game.temptWithTheRing(controller.getId());

            // make sure the new ringbearer has been chosen.
            game.getState().processAction(game);
            controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }

            Permanent ringbearer = controller.getRingBearer(game);
            if (ringbearer != null) {
                // then you put a +1/+1 counter on your Ring-bearer.
                ringbearer.addCounters(CounterType.P1P1.createInstance(), controller.getId(), source, game);
            }

            return true;
        } else {
            // If guidance gets more votes or the vote is tied, draw a card.
            controller.drawCards(1, source, game);
            return true;
        }
    }
}