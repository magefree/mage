package mage.cards.c;

import java.util.*;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.MageItem;
import mage.MageObjectReference;
import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.CanPlayCardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.Cards;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author MorellThomas
 */
public final class ChissGoriaForgeTyrant extends CardImpl {

  public ChissGoriaForgeTyrant(UUID ownerId, CardSetInfo setInfo) {
    super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}{R}");
        
    this.supertype.add(SuperType.LEGENDARY);
    this.subtype.add(SubType.DRAGON);
    this.power = new MageInt(5);
    this.toughness = new MageInt(4);

        // Affinity for artifacts
    this.addAbility(new AffinityForArtifactsAbility());
        // Flying
    this.addAbility(FlyingAbility.getInstance());

        // Haste
    this.addAbility(HasteAbility.getInstance());

        // Whenever Chiss-Goria, Forge Tyrant attacks, exile the top five cards of your library. You may cast an artifact spell from among them this turn. If you do, it has affinity for artifacts.
    this.addAbility(new AttacksTriggeredAbility(new ChissGoriaForgeTyrantEffect(), false), new ChissGoriaForgeTyrantWatcher());

    }

    private ChissGoriaForgeTyrant(final ChissGoriaForgeTyrant card) {
        super(card);
    }

    @Override
    public ChissGoriaForgeTyrant copy() {
        return new ChissGoriaForgeTyrant(this);
    }
}

class ChissGoriaForgeTyrantEffect extends OneShotEffect {

  public ChissGoriaForgeTyrantEffect() {
      super(Outcome.Benefit);
      staticText = "exile the top five cards of your library. " 
    + "You may cast an artifact spell from among them this turn. "
    + "If you do, it has affinity for artifacts.";
  }

  private ChissGoriaForgeTyrantEffect(final ChissGoriaForgeTyrantEffect effect) {
    super(effect);
  }

  @Override
  public ChissGoriaForgeTyrantEffect copy() {
    return new ChissGoriaForgeTyrantEffect(this);
  }

  @Override
  public boolean apply(Game game, Ability source) {
    Player controller = game.getPlayer(source.getControllerId());
    
    FilterCard filter = new FilterArtifactCard("artifact");
    filter.add(Predicates.not(CardType.LAND.getPredicate()));

    // move top 5 from library to exile
    Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
    Set<Card> cardSet = cards.getCards(game);
    controller.moveCardsToExile(cardSet, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));

    ChissGoriaForgeTyrantWatcher.addCards(source, cardSet, game);
    for (Card card : cardSet) {
      if (filter.match(card, game)) {
        game.addEffect(new ChissGoriaForgeTyrantCastEffect(card, game), source);
        card.setOwnerId(source.getControllerId());
        card.addAbility(new AffinityForArtifactsAbility());
        card.setOwnerId(source.getControllerId());
      }
    }

    return true;
  }
}

class ChissGoriaForgeTyrantCastEffect extends CanPlayCardControllerEffect {

    private final FilterControlledPermanent filter = StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT;

    ChissGoriaForgeTyrantCastEffect(Card card, Game game) {
        super(game, card.getMainCard().getId(), card.getZoneChangeCounter(game), Duration.EndOfTurn);
    }

    private ChissGoriaForgeTyrantCastEffect(final ChissGoriaForgeTyrantCastEffect effect) {
        super(effect);
    }

    @Override
    public ChissGoriaForgeTyrantCastEffect copy() {
        return new ChissGoriaForgeTyrantCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
      Player player = game.getPlayer(source.getControllerId());
      if (!super.applies(objectId, source, affectedControllerId, game) || !ChissGoriaForgeTyrantWatcher.checkCard(game, source, mor)) {
        return false;
      }
      return true;
    }
}

class ChissGoriaForgeTyrantWatcher extends Watcher {

    private final Map<MageObjectReference, Set<Set<MageObjectReference>>> morMap = new HashMap<>();

    public ChissGoriaForgeTyrantWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {
            morMap.entrySet().removeIf(e -> !e.getKey().zoneCounterIsCurrent(game));
            morMap.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .forEach(set -> set.removeIf(mor -> !mor.zoneCounterIsCurrent(game)));
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
                .getWatcher(ChissGoriaForgeTyrantWatcher.class)
                .morMap
                .computeIfAbsent(new MageObjectReference(source), x -> new HashSet<>())
                .add(cards
                        .stream()
                        .map(card -> new MageObjectReference(card, game))
                        .collect(Collectors.toSet()));
    }

    static boolean checkCard(Game game, Ability source, MageObjectReference mor) {
        return game.getState()
                .getWatcher(ChissGoriaForgeTyrantWatcher.class)
                .morMap
                .getOrDefault(new MageObjectReference(source), Collections.emptySet())
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(mor::equals);
    }
}

