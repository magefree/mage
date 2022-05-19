package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BattleAngelsOfTyr extends CardImpl {

    public BattleAngelsOfTyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Myriad
        this.addAbility(new MyriadAbility());

        // Whenever Battle Angels of Tyr deals combat damage to a player, draw a card if that player has more cards in hand than each other player. Then you create a Treasure token if that player controls more lands than each other player. Then you gain 3 life if that player has more life than each other player.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new BattleAngelsOfTyrEffect(), false, true
        ));
    }

    private BattleAngelsOfTyr(final BattleAngelsOfTyr card) {
        super(card);
    }

    @Override
    public BattleAngelsOfTyr copy() {
        return new BattleAngelsOfTyr(this);
    }
}

class BattleAngelsOfTyrEffect extends OneShotEffect {

    BattleAngelsOfTyrEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card if that player has more cards in hand than each other player. " +
                "Then you create a Treasure token if that player controls more lands than each other player. " +
                "Then you gain 3 life if that player has more life than each other player";
    }

    private BattleAngelsOfTyrEffect(final BattleAngelsOfTyrEffect effect) {
        super(effect);
    }

    @Override
    public BattleAngelsOfTyrEffect copy() {
        return new BattleAngelsOfTyrEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Set<Player> players = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .filter(uuid -> !player.getId().equals(uuid))
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (players
                .stream()
                .map(Player::getHand)
                .mapToInt(Set::size)
                .allMatch(x -> player.getHand().size() > x)) {
            controller.drawCards(1, source, game);
        }
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), game)
                .stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(
                        Function.identity(), x -> 1,
                        CardUtil::setOrIncrementValue
                ));
        if (map.getOrDefault(player.getId(), 0) >
                map.entrySet()
                        .stream()
                        .filter(e -> !player.getId().equals(e.getKey()))
                        .mapToInt(Map.Entry::getValue)
                        .max()
                        .orElse(0)) {
            new TreasureToken().putOntoBattlefield(1, game, source);
        }
        if (players
                .stream()
                .mapToInt(Player::getLife)
                .allMatch(x -> player.getLife() > x)) {
            controller.gainLife(3, game, source);
        }
        return true;
    }
}
