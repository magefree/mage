package mage.game.command.dungeons;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.game.command.DungeonRoom;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TheAtropalToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetDiscard;

import java.util.*;

/**
 * @author TheElk801
 */
public final class TombOfAnnihilationDungeon extends Dungeon {

    static final FilterControlledPermanent filter
            = new FilterControlledPermanent("an artifact, a creature, or a land");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public TombOfAnnihilationDungeon() {
        super("Tomb of Annihilation");
        // (1) Trapped Entry — Each player loses 1 life. (→ 2a or 2b)
        DungeonRoom trappedEntry = new DungeonRoom("Trapped Entry", new LoseLifeAllPlayersEffect(1));

        // (2a) Veils of Fear — Each player loses 2 life unless they discard a card. (→ 3)
        DungeonRoom veilsOfFear = new DungeonRoom("Veils of Fear", new VeilsOfFearEffect());

        // (2b) Oubliette — Discard a card and sacrifice an artifact, a creature, and a land. (→ 4)
        DungeonRoom oubliette = new DungeonRoom("Oubliette", new OublietteEffect());

        // (3) Sandfall Cell — Each player loses 2 life unless they sacrifice an artifact, a creature, or a land. (→ 4)
        DungeonRoom sandfallCell = new DungeonRoom("Sandfall Cell", new SandfallCellEffect());

        // (4) Cradle of the Death God — Create The Atropal, a legendary 4/4 black God Horror creature token with deathtouch.
        DungeonRoom cradleOfTheDeathGod = new DungeonRoom("Cradle of the Death God", new CreateTokenEffect(new TheAtropalToken()));

        trappedEntry.addNextRoom(veilsOfFear);
        trappedEntry.addNextRoom(oubliette);
        veilsOfFear.addNextRoom(sandfallCell);
        oubliette.addNextRoom(cradleOfTheDeathGod);
        sandfallCell.addNextRoom(cradleOfTheDeathGod);

        this.addRoom(trappedEntry);
        this.addRoom(veilsOfFear);
        this.addRoom(oubliette);
        this.addRoom(sandfallCell);
        this.addRoom(cradleOfTheDeathGod);
    }

    private TombOfAnnihilationDungeon(final TombOfAnnihilationDungeon dungeon) {
        super(dungeon);
    }

    public TombOfAnnihilationDungeon copy() {
        return new TombOfAnnihilationDungeon(this);
    }
}

class VeilsOfFearEffect extends OneShotEffect {

    VeilsOfFearEffect() {
        super(Outcome.Neutral);
        staticText = "each player loses 2 life unless they discard a card";
    }

    private VeilsOfFearEffect(final VeilsOfFearEffect effect) {
        super(effect);
    }

    @Override
    public VeilsOfFearEffect copy() {
        return new VeilsOfFearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Card> map = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetDiscard target = new TargetDiscard(0, 1, StaticFilters.FILTER_CARD, playerId);
            player.choose(Outcome.PreventDamage, target, source, game);
            map.put(playerId, game.getCard(target.getFirstTarget()));
        }
        for (Map.Entry<UUID, Card> entry : map.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            if (entry.getValue() != null) {
                player.discard(entry.getValue(), false, source, game);
            } else {
                player.loseLife(2, game, source, false);
            }
        }
        return true;
    }
}

class OublietteEffect extends OneShotEffect {

    OublietteEffect() {
        super(Outcome.Sacrifice);
        staticText = "discard a card and sacrifice an artifact, a creature, and a land";
    }

    private OublietteEffect(final OublietteEffect effect) {
        super(effect);
    }

    @Override
    public OublietteEffect copy() {
        return new OublietteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.discard(1, false, false, source, game);
        int saccable = OublietteTarget.checkTargetCount(source, game);
        if (saccable < 1) {
            return true;
        }
        OublietteTarget target = new OublietteTarget(Math.min(saccable, 3));
        player.choose(Outcome.Sacrifice, target, source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}

class OublietteTarget extends TargetControlledPermanent {

    private static final CardTypeAssignment cardTypeAssigner = new CardTypeAssignment(
            CardType.ARTIFACT,
            CardType.CREATURE,
            CardType.LAND
    );
    private static final FilterControlledPermanent filter = TombOfAnnihilationDungeon.filter.copy();

    static {
        filter.setMessage("an artifact, a creature, and a land");
    }

    OublietteTarget(int numTargets) {
        super(numTargets, numTargets, filter, true);
    }

    private OublietteTarget(final OublietteTarget target) {
        super(target);
    }

    @Override
    public OublietteTarget copy() {
        return new OublietteTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(permanent);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        possibleTargets.removeIf(uuid -> !this.canTarget(sourceControllerId, uuid, null, game));
        return possibleTargets;
    }

    static int checkTargetCount(Ability source, Game game) {
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game);
        return cardTypeAssigner.getRoleCount(new CardsImpl(permanents), game);
    }
}

class SandfallCellEffect extends OneShotEffect {

    SandfallCellEffect() {
        super(Outcome.Neutral);
        staticText = "each player loses 2 life unless they sacrifice an artifact, a creature, or a land";
    }

    private SandfallCellEffect(final SandfallCellEffect effect) {
        super(effect);
    }

    @Override
    public SandfallCellEffect copy() {
        return new SandfallCellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Permanent> map = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(0, 1, TombOfAnnihilationDungeon.filter, true);
            player.choose(Outcome.PreventDamage, target, source, game);
            map.put(playerId, game.getPermanent(target.getFirstTarget()));
        }
        for (Map.Entry<UUID, Permanent> entry : map.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            if (entry.getValue() != null) {
                entry.getValue().sacrifice(source, game);
            } else {
                player.loseLife(2, game, source, false);
            }
        }
        return true;
    }
}

