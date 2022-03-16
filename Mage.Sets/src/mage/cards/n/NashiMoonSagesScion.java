package mage.cards.n;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.CanPlayCardControllerEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class NashiMoonSagesScion extends CardImpl {

    public NashiMoonSagesScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ninjutsu {3}{B}
        this.addAbility(new NinjutsuAbility("{3}{B}"));

        // Whenever Nashi, Moon Sage's Scion deals combat damage to a player, exile the top card of each player's library. Until end of turn, you may play one of those cards. If you cast a spell this way, pay life equal to its mana value rather than paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new NashiMoonSagesScionEffect(), false
        ), new NashiMoonSagesScionWatcher());
    }

    private NashiMoonSagesScion(final NashiMoonSagesScion card) {
        super(card);
    }

    @Override
    public NashiMoonSagesScion copy() {
        return new NashiMoonSagesScion(this);
    }
}

class NashiMoonSagesScionEffect extends OneShotEffect {

    public NashiMoonSagesScionEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of each player's library. Until end of turn, " +
                "you may play one of those cards. If you cast a spell this way, " +
                "pay life equal to its mana value rather than paying its mana cost";
    }

    public NashiMoonSagesScionEffect(final NashiMoonSagesScionEffect effect) {
        super(effect);
    }

    @Override
    public NashiMoonSagesScionEffect copy() {
        return new NashiMoonSagesScionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                cards.add(player.getLibrary().getFromTop(game));
            }
        }
        Set<Card> cardSet = cards.getCards(game);
        controller.moveCardsToExile(
                cardSet, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        NashiMoonSagesScionWatcher.addCards(source, cardSet, game);
        for (Card card : cardSet) {
            game.addEffect(new NashiMoonSagesScionPlayEffect(game, card), source);
        }
        return true;
    }
}

class NashiMoonSagesScionWatcher extends Watcher {

    private final Map<MageObjectReference, Set<Set<MageObjectReference>>> morMap = new HashMap<>();

    public NashiMoonSagesScionWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {
            morMap.entrySet().removeIf(e -> !e.getKey().zoneCounterIsCurrent(game));
            morMap.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .map(set -> set.removeIf(mor -> !mor.zoneCounterIsCurrent(game)));
            morMap.values().removeIf(Set::isEmpty);
            return;
        }
        if (event.getType() != GameEvent.EventType.SPELL_CAST || event.getAdditionalReference() == null) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null) {
            return;
        }
        morMap.getOrDefault(
                event.getAdditionalReference().getApprovingMageObjectReference(), Collections.emptySet()
        ).removeIf(set -> set
                .stream()
                .anyMatch(mor -> mor.getSourceId().equals(spell.getMainCard().getId())
                        && mor.getZoneChangeCounter() + 1 == spell.getZoneChangeCounter(game)));
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
    }

    static void addCards(Ability source, Set<Card> cards, Game game) {
        game.getState()
                .getWatcher(NashiMoonSagesScionWatcher.class)
                .morMap
                .computeIfAbsent(new MageObjectReference(source), x -> new HashSet<>())
                .add(cards
                        .stream()
                        .map(card -> new MageObjectReference(card, game))
                        .collect(Collectors.toSet()));
    }

    static boolean checkCard(Game game, Ability source, MageObjectReference mor) {
        return game.getState()
                .getWatcher(NashiMoonSagesScionWatcher.class)
                .morMap
                .getOrDefault(new MageObjectReference(source), Collections.emptySet())
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(mor::equals);
    }
}

class NashiMoonSagesScionPlayEffect extends CanPlayCardControllerEffect {

    NashiMoonSagesScionPlayEffect(Game game, Card card) {
        super(game, card.getMainCard().getId(), card.getZoneChangeCounter(game), Duration.EndOfTurn);
    }

    private NashiMoonSagesScionPlayEffect(final NashiMoonSagesScionPlayEffect effect) {
        super(effect);
    }

    @Override
    public NashiMoonSagesScionPlayEffect copy() {
        return new NashiMoonSagesScionPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!super.applies(objectId, source, affectedControllerId, game)
                || !NashiMoonSagesScionWatcher.checkCard(game, source, mor)) {
            return false;
        }
        Card cardToCheck = mor.getCard(game);
        if (cardToCheck.isLand(game)) {
            return true;
        }
        // allows to play/cast with alternative life cost
        Player controller = game.getPlayer(source.getControllerId());
        PayLifeCost lifeCost = new PayLifeCost(cardToCheck.getSpellAbility().getManaCosts().manaValue());
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.add(lifeCost);
        newCosts.addAll(cardToCheck.getSpellAbility().getCosts());
        controller.setCastSourceIdWithAlternateMana(cardToCheck.getId(), null, newCosts);
        return true;
    }
}
