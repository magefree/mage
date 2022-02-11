/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.k;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class KotoseTheSilentSpider extends CardImpl {

    public KotoseTheSilentSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Kotose, Silent Spider enters the battlefield, exile target card in an opponent's graveyard other than a basic land card. Search that player's graveyard, hand, and library for any number of cards with the same name as that card and exile them. For as long as you control Kotose, you may play one of the exiled cards, and you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KotoseTheSilentSpiderEffect()), new KotoseTheSilentSpiderWatcher(super.getId()));

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

    private static final FilterCard filter = new FilterCard("card other than a basic land card in an opponent's graveyard");

    static {
        filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
    }

    Cards storeCardsThatWereInExile = new CardsImpl();
    Set<Card> cardsToExile = new HashSet<>();

    public KotoseTheSilentSpiderEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target card in an opponent's graveyard other than a basic land card. Search that player's graveyard, hand, and library for any number of cards with the same name as that card and exile them. For as long as you control Kotose, you may play one of the exiled cards, and you may spend mana as though it were mana of any color to cast it.";
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
        storeCardsThatWereInExile.clear();
        cardsToExile.clear();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(filter);
            if (controller.choose(Outcome.Neutral, target, source.getSourceId(), game)
                    && game.getCard(target.getFirstTarget()) != null) {
                Card chosenCard = game.getCard(target.getFirstTarget());
                controller.moveCardsToExile(chosenCard, source, game, true, exileId, game.getObject(source.getSourceId()).getIdName());
                Player exiledCardOwner = game.getPlayer(game.getOwnerId(chosenCard.getId()));
                if (exiledCardOwner == null) {
                    return false;
                }
                // search their graveyard
                FilterCard filterCardSameNameAsTargetedExiled = new FilterCard();
                filterCardSameNameAsTargetedExiled.add(new NamePredicate(chosenCard.getName()));
                filterCardSameNameAsTargetedExiled.add(new OwnerIdPredicate(exiledCardOwner.getId()));
                Set<Card> cardsInGraveyard = exiledCardOwner.getGraveyard().getCards(game);
                Cards cards = new CardsImpl();
                cards.addAll(cardsInGraveyard);
                TargetCard targetCards = new TargetCard(0, Integer.MAX_VALUE, Zone.GRAVEYARD, filterCardSameNameAsTargetedExiled.withMessage("card(s) with the same name as the chosen card from the owner's graveyard"));
                if (controller.choose(outcome, cards, targetCards, game)) {
                    for (UUID cardId : targetCards.getTargets()) {
                        cardsToExile.add(game.getCard(cardId));
                    }
                }
                // search their hand
                Set<Card> cardsInHand = exiledCardOwner.getHand().getCards(game);
                Cards cards2 = new CardsImpl();
                cards2.addAll(cardsInHand);
                TargetCard targetCards3 = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filterCardSameNameAsTargetedExiled.withMessage("card(s) with the same name as the chosen card from the owner's hand"));
                if (controller.choose(outcome, cards2, targetCards3, game)) {
                    for (UUID cardId : targetCards3.getTargets()) {
                        cardsToExile.add(game.getCard(cardId));
                    }
                }
                // search their library
                List<Card> cardsInLibrary = exiledCardOwner.getLibrary().getCards(game);
                Cards cards3 = new CardsImpl();
                cards3.addAll(cardsInLibrary);
                TargetCard targetCards4 = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filterCardSameNameAsTargetedExiled.withMessage("card(s) with the same name as the chosen card from the owner's library"));
                if (controller.choose(outcome, cards3, targetCards4, game)) {
                    for (UUID cardId : targetCards4.getTargets()) {
                        cardsToExile.add(game.getCard(cardId));
                    }
                }
                // move all the chosen cards to exile at the same time
                cardsToExile.add(chosenCard);  // don't forget the chosen card!
                controller.moveCardsToExile(cardsToExile, source, game, true, exileId, game.getObject(source.getSourceId()).getIdName());
            }
            // used as a check for any cards that were put into exile (when cast, they are moved to the stack, etc)
            storeCardsThatWereInExile.addAll(cardsToExile);

            KotoseTheSilentSpiderPlayEffect effect = new KotoseTheSilentSpiderPlayEffect();
            ConditionalAsThoughEffect conditionalEffect = new ConditionalAsThoughEffect(effect, new KotoseTheSilentSpiderCondition());
            conditionalEffect.setDuration(Duration.WhileControlled);
            game.getState().setValue("Result Kotose" + source.getSourceId().toString(), Boolean.TRUE);
            for (Card exiledCard : cardsToExile) {
                if (game.getState().getZone(exiledCard.getId()) == Zone.EXILED) {
                    storeCardsThatWereInExile.add(exiledCard);
                    conditionalEffect.setTargetPointer(new FixedTarget(exiledCard.getId(), game));
                    game.addEffect(conditionalEffect, source);
                }
            }
            game.addEffect(new KotoseTheSilentSpiderAnyColorManaEffect(), source);
            game.getState().setValue("Cards Exile Kotose" + source.getSourceId().toString(), storeCardsThatWereInExile);
            return true;
        }
        return false;
    }
}

class KotoseTheSilentSpiderCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatcher(KotoseTheSilentSpiderWatcher.class);
        return (watcher != null
                && watcher.conditionMet());
    }
}

class KotoseTheSilentSpiderWatcher extends Watcher {

    UUID sourceCardId;

    public KotoseTheSilentSpiderWatcher(UUID sourceCardId) {
        super(WatcherScope.GAME);
        this.sourceCardId = sourceCardId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (game.getState().getValue("Result Kotose" + sourceCardId.toString()) != null) {
            condition = (Boolean) game.getState().getValue("Result Kotose" + sourceCardId.toString());
        }
        UUID exileId = CardUtil.getExileZoneId(game, sourceCardId, game.getState().getZoneChangeCounter(sourceCardId));
        if (game.getState().getExile().getExileZone(exileId) != null
                && game.getState().getExile().getExileZone(exileId).size() > 0) {
            Cards cards = (Cards) game.getState().getValue("Cards Exile Kotose" + sourceCardId.toString());
            // Cast spell
            if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                Spell spell = (Spell) game.getObject(event.getSourceId());
                if (spell != null
                        && cards != null
                        && !cards.isEmpty()
                        && cards.contains(spell.getSourceId())) {
                    Ability approvingAbility = event.getAdditionalReference().getApprovingAbility();
                    if (approvingAbility != null
                            && approvingAbility.getSourceId().equals(sourceCardId)) {
                        condition = false;
                        game.getState().setValue("Result Kotose" + sourceCardId.toString(), Boolean.FALSE);
                    }
                }
            }
            // Play land
            if (event.getType() == GameEvent.EventType.PLAY_LAND) {
                Card land = game.getCard(event.getSourceId());
                if (land != null
                        && cards != null
                        && !cards.isEmpty()
                        && cards.contains(land.getId())) {
                    Ability approvingAbility = event.getAdditionalReference().getApprovingAbility();
                    if (approvingAbility != null
                            && approvingAbility.getSourceId().equals(sourceCardId)) {
                        condition = false;
                        game.getState().setValue("Result Kotose" + sourceCardId.toString(), Boolean.FALSE);
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}

class KotoseTheSilentSpiderPlayEffect extends AsThoughEffectImpl {

    KotoseTheSilentSpiderPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE,
                Duration.WhileControlled, Outcome.Benefit);
        staticText = "";
    }

    private KotoseTheSilentSpiderPlayEffect(final KotoseTheSilentSpiderPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KotoseTheSilentSpiderPlayEffect copy() {
        return new KotoseTheSilentSpiderPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {

        Player controller = game.getPlayer(affectedControllerId);
        if (controller == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());

        Set<Card> cards = game.getState().getExile().getExileZone(exileId).getCards(game);
        List<UUID> targetsTest = getTargetPointer().getTargets(game, source);
        if (cards != null
                && targetsTest != null) {
            for (UUID uuid : targetsTest) {
                if (!cards.contains(game.getCard(uuid))) {
                    getTargetPointer().getTargets(game, source).remove(uuid);
                }
            }
        }

        if (game.getState().getValue("Result Kotose" + source.getSourceId().toString()) == Boolean.FALSE) {
            this.discard();
            return false;
        }

        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            this.discard();
            return false;
        }

        UUID objectIdToCast = CardUtil.getMainCardId(game, objectId);
        if (!targets.contains(objectIdToCast)) {
            return false;
        }

        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        // must be in exile
        if (game.getState().getZone(objectId) != Zone.EXILED) {
            return false;
        }

        // everything checks out, so allow it to be played
        return true;
    }
}

class KotoseTheSilentSpiderAnyColorManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    KotoseTheSilentSpiderAnyColorManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileControlled, Outcome.Benefit);
        this.staticText = ", and you may spend mana as though it were mana of any color to cast it";
    }

    private KotoseTheSilentSpiderAnyColorManaEffect(final KotoseTheSilentSpiderAnyColorManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KotoseTheSilentSpiderAnyColorManaEffect copy() {
        return new KotoseTheSilentSpiderAnyColorManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Cards cards = (Cards) game.getState().getValue("Cards Exile Kotose" + source.getSourceId().toString());
        if (cards == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
        return source.isControlledBy(affectedControllerId)
                && exileZone != null
                && !cards.isEmpty()
                && cards.contains(CardUtil.getMainCardId(game, objectId));
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
