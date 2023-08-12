package mage.cards.z;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DevilToken;
import mage.players.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZurzothChaosRider extends CardImpl {

    public ZurzothChaosRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an opponent draws their first card each turn, if it's not their turn, you create a 1/1 red Devil creature token with "When this creature dies, it deals 1 damage to any target."
        this.addAbility(new ZurzothChaosRiderDrawAbility());

        // Whenever one or more Devils you control attack one or more players, you and those players each draw a card, then discard a card at random.
        this.addAbility(new ZurzothChaosRiderAttackAbility());
    }

    private ZurzothChaosRider(final ZurzothChaosRider card) {
        super(card);
    }

    @Override
    public ZurzothChaosRider copy() {
        return new ZurzothChaosRider(this);
    }
}

class ZurzothChaosRiderDrawAbility extends TriggeredAbilityImpl {

    private final Set<UUID> playerIds = new HashSet<>();

    ZurzothChaosRiderDrawAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new DevilToken()));
    }

    private ZurzothChaosRiderDrawAbility(final ZurzothChaosRiderDrawAbility ability) {
        super(ability);
        this.playerIds.addAll(ability.playerIds);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD
                || event.getType() == GameEvent.EventType.END_PHASE_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case END_PHASE_POST:
                playerIds.clear();
                return false;
            case DREW_CARD:
                if (playerIds.contains(event.getPlayerId())) {
                    return false;
                }
                playerIds.add(event.getPlayerId());
                return !game.isActivePlayer(event.getPlayerId())
                        && game.getOpponents(this.getControllerId()).contains(event.getPlayerId());
            default:
                return false;
        }
    }

    @Override
    public ZurzothChaosRiderDrawAbility copy() {
        return new ZurzothChaosRiderDrawAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent draws their first card each turn, if it's not their turn, " +
                "you create a 1/1 red Devil creature token with \"When this creature dies, it deals 1 damage to any target.\"";
    }
}

class ZurzothChaosRiderAttackAbility extends TriggeredAbilityImpl {

    ZurzothChaosRiderAttackAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private ZurzothChaosRiderAttackAbility(final ZurzothChaosRiderAttackAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> playerIds = new HashSet<>();
        game.getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isControlledBy(this.getControllerId()))
                .filter(permanent -> permanent.hasSubtype(SubType.DEVIL, game))
                .map(MageItem::getId)
                .map(game.getCombat()::getDefenderId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .forEach(playerIds::add);
        if (playerIds.isEmpty()) {
            return false;
        }
        playerIds.add(this.getControllerId());
        this.getEffects().clear();
        this.addEffect(new ZurzothChaosRiderEffect(playerIds));
        return true;
    }

    @Override
    public ZurzothChaosRiderAttackAbility copy() {
        return new ZurzothChaosRiderAttackAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more Devils you control attack one or more players, " +
                "you and those players each draw a card, then discard a card at random.";
    }
}

class ZurzothChaosRiderEffect extends OneShotEffect {

    private final Set<UUID> playerIds = new HashSet<>();

    ZurzothChaosRiderEffect(Set<UUID> playerIds) {
        super(Outcome.Benefit);
        this.playerIds.addAll(playerIds);
    }

    private ZurzothChaosRiderEffect(final ZurzothChaosRiderEffect effect) {
        super(effect);
        this.playerIds.addAll(effect.playerIds);
    }

    @Override
    public ZurzothChaosRiderEffect copy() {
        return new ZurzothChaosRiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!playerIds.contains(playerId)) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.drawCards(1, source, game);
            player.discard(1, true, false, source, game);
        }
        return true;
    }
}
