package mage.cards.c;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.VoteHandler;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CirdanTheShipwright extends CardImpl {

    public CirdanTheShipwright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Secret council -- Whenever Cirdan the Shipwright enters the battlefield or attacks, each player secretly votes for a player, then those votes are revealed. Each player draws a card for each vote they received. Each player who received no votes may put a permanent card from their hand onto the battlefield.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CirdanTheShipwrightEffect()).setAbilityWord(AbilityWord.SECRET_COUNCIL));
    }

    private CirdanTheShipwright(final CirdanTheShipwright card) {
        super(card);
    }

    @Override
    public CirdanTheShipwright copy() {
        return new CirdanTheShipwright(this);
    }
}

class CirdanTheShipwrightEffect extends OneShotEffect {

    CirdanTheShipwrightEffect() {
        super(Outcome.Benefit);
        staticText = "each player secretly votes for a player, then those votes are revealed. " +
                "Each player draws a card for each vote they received. " +
                "Each player who received no votes may put a permanent card from their hand onto the battlefield";
    }

    private CirdanTheShipwrightEffect(final CirdanTheShipwrightEffect effect) {
        super(effect);
    }

    @Override
    public CirdanTheShipwrightEffect copy() {
        return new CirdanTheShipwrightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CirdanTheShipwrightVote vote = new CirdanTheShipwrightVote();
        vote.doVotes(source, game);
        Map<UUID, Integer> playerMap = vote.getVotesPerPlayer(game);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            int amount = playerMap.getOrDefault(playerId, 0);
            if (player != null && amount > 0) {
                player.drawCards(amount, source, game);
            }
        }
        Map<Player, Card> voteless = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && !playerMap.containsKey(playerId)) {
                TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_PERMANENT);
                target.withChooseHint("to put onto the battlefield");
                player.choose(outcome, player.getHand(), target, source, game);
                voteless.put(player, game.getCard(target.getFirstTarget()));
            }
        }
        for (Map.Entry<Player, Card> entry : voteless.entrySet()) {
            if (entry.getValue() != null) {
                entry.getKey().moveCards(entry.getValue(), Zone.BATTLEFIELD, source, game);
            }
        }
        return true;
    }
}

class CirdanTheShipwrightVote extends VoteHandler<Player> {

    CirdanTheShipwrightVote() {
        this.secret = true;
    }

    @Override
    protected Set<Player> getPossibleVotes(Ability source, Game game) {
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    protected Player playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        TargetPlayer target = new TargetPlayer();
        target.setNotTarget(true);
        target.withChooseHint("to vote for");
        decidingPlayer.choose(Outcome.Benefit, target, source, game);
        return game.getPlayer(target.getFirstTarget());
    }

    @Override
    protected String voteName(Player vote) {
        return vote.getName();
    }

    Map<UUID, Integer> getVotesPerPlayer(Game game) {
        return playerMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(MageItem::getId, x -> 1, Integer::sum));
    }
}
