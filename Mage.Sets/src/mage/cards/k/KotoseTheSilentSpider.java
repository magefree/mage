package mage.cards.k;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class KotoseTheSilentSpider extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("card other than a basic land card in an opponent's graveyard");

    static {
        filter.add(Predicates.not(Predicates.and(
                CardType.LAND.getPredicate(),
                SuperType.BASIC.getPredicate()
        )));
    }

    public KotoseTheSilentSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Kotose, Silent Spider enters the battlefield, exile target card in an opponent's graveyard other than a basic land card. Search that player's graveyard, hand, and library for any number of cards with the same name as that card and exile them. For as long as you control Kotose, you may play one of the exiled cards, and you may spend mana as though it were mana of any color to cast it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KotoseTheSilentSpiderEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability, new KotoseTheSilentSpiderWatcher());
    }

    private KotoseTheSilentSpider(final KotoseTheSilentSpider card) {
        super(card);
    }

    @Override
    public KotoseTheSilentSpider copy() {
        return new KotoseTheSilentSpider(this);
    }
}

class KotoseTheSilentSpiderEffect extends OneShotEffect {

    public KotoseTheSilentSpiderEffect() {
        super(Outcome.Exile);
        this.staticText = "exile target card other than a basic land card from an opponent's graveyard. " +
                "Search that player's graveyard, hand, and library for any number of cards with the same name " +
                "as that card and exile them. Then that player shuffles. For as long as you control {this}, you may " +
                "play one of the exiled cards, and you may spend mana as though it were mana of any color to cast it";
    }

    public KotoseTheSilentSpiderEffect(final KotoseTheSilentSpiderEffect effect) {
        super(effect);
    }

    @Override
    public KotoseTheSilentSpiderEffect copy() {
        return new KotoseTheSilentSpiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        Player opponent = game.getPlayer(card.getOwnerId());
        if (opponent == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        String exileName = CardUtil.getSourceName(game, source);
        controller.moveCardsToExile(card, source, game, true, exileId, exileName);
        Cards cards = new CardsImpl();
        FilterCard filter = new FilterCard("cards named " + card.getName() + " from " + opponent.getName() + "'s graveyard");
        filter.add(new NamePredicate(card.getName()));

        TargetCardInGraveyard targetCardInGraveyard = new TargetCardInGraveyard(0, Integer.MAX_VALUE, filter);
        controller.choose(outcome, opponent.getGraveyard(), targetCardInGraveyard, game);
        cards.addAll(targetCardInGraveyard.getTargets());

        filter.setMessage("cards named " + card.getName() + " from " + opponent.getName() + "'s hand");
        TargetCardInHand targetCardInHand = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
        controller.choose(outcome, opponent.getHand(), targetCardInHand, game);
        cards.addAll(targetCardInHand.getTargets());

        filter.setMessage("cards named " + card.getName() + " from " + opponent.getName() + "'s library");
        TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
        controller.searchLibrary(target, source, game, opponent.getId());
        target.getTargets()
                .stream()
                .map(cardId -> opponent.getLibrary().getCard(cardId, game))
                .forEach(cards::add);

        Set<Card> cardSet = cards.getCards(game);
        controller.moveCardsToExile(cardSet, source, game, true, exileId, exileName);
        opponent.shuffleLibrary(source, game);
        cardSet.add(card);
        if (cardSet.isEmpty() || source.getSourcePermanentIfItStillExists(game) == null) {
            return true;
        }
        KotoseTheSilentSpiderWatcher.addCards(source, cardSet, game);
        for (Card exiledCard : cardSet) {
            CardUtil.makeCardPlayable(
                    game, source, exiledCard, Duration.WhileControlled, true,
                    null, new KotoseTheSilentSpiderCondition(exiledCard, game)
            );
        }
        return true;
    }
}

class KotoseTheSilentSpiderCondition implements Condition {

    private final MageObjectReference mor;

    KotoseTheSilentSpiderCondition(Card card, Game game) {
        this.mor = new MageObjectReference(card, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return KotoseTheSilentSpiderWatcher.checkCard(game, source, mor);
    }
}

class KotoseTheSilentSpiderWatcher extends Watcher {

    private final Map<MageObjectReference, Set<Set<MageObjectReference>>> morMap = new HashMap<>();

    public KotoseTheSilentSpiderWatcher() {
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

    static void addCards(Ability source, Set<Card> cards, Game game) {
        game.getState()
                .getWatcher(KotoseTheSilentSpiderWatcher.class)
                .morMap
                .computeIfAbsent(new MageObjectReference(source), x -> new HashSet<>())
                .add(cards
                        .stream()
                        .map(card -> new MageObjectReference(card, game))
                        .collect(Collectors.toSet()));
    }

    static boolean checkCard(Game game, Ability source, MageObjectReference mor) {
        return game.getState()
                .getWatcher(KotoseTheSilentSpiderWatcher.class)
                .morMap
                .getOrDefault(new MageObjectReference(source), Collections.emptySet())
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(mor::equals);
    }
}
