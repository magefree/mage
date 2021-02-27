package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.VotedEvent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrudgeKeeper extends CardImpl {

    public GrudgeKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever players finish voting, each opponent who voted for a choice you didn't vote for loses 2 life.
        this.addAbility(new GrudgeKeeperTriggeredAbility());
    }

    private GrudgeKeeper(final GrudgeKeeper card) {
        super(card);
    }

    @Override
    public GrudgeKeeper copy() {
        return new GrudgeKeeper(this);
    }
}

class GrudgeKeeperTriggeredAbility extends TriggeredAbilityImpl {

    GrudgeKeeperTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private GrudgeKeeperTriggeredAbility(final GrudgeKeeperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VOTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        VotedEvent votedEvent = (VotedEvent) event;
        this.getEffects().clear();
        this.addEffect(new GrudgeKeeperEffect(votedEvent.getDidntVote(getControllerId())));
        return true;
    }

    @Override
    public GrudgeKeeperTriggeredAbility copy() {
        return new GrudgeKeeperTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever players finish voting, each opponent who voted for a choice you didn't vote for loses 2 life.";
    }
}

class GrudgeKeeperEffect extends OneShotEffect {

    private final Set<UUID> playerIds = new HashSet<>();

    GrudgeKeeperEffect(Set<UUID> playerIds) {
        super(Outcome.Benefit);
        this.playerIds.addAll(playerIds);
    }

    private GrudgeKeeperEffect(final GrudgeKeeperEffect effect) {
        super(effect);
        this.playerIds.addAll(effect.playerIds);
    }

    @Override
    public GrudgeKeeperEffect copy() {
        return new GrudgeKeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : playerIds) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.hasOpponent(source.getControllerId(), game)) {
                player.loseLife(2, game, source, false);
            }
        }
        return true;
    }
}
