package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.Costs;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author credman0
 */
public class AminatousAugury extends CardImpl {

    public AminatousAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}{U}");

        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a card of that type from among the exiled cards
        // without paying its mana cost.
        this.getSpellAbility().addEffect(new AminatousAuguryEffect());
    }

    public AminatousAugury(final AminatousAugury card) {
        super(card);
    }

    @Override
    public AminatousAugury copy() {
        return new AminatousAugury(this);
    }

}

class AminatousAuguryEffect extends OneShotEffect{

    public AminatousAuguryEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile the top eight cards of your library. You may put a land card from among them onto the" +
                " battlefield. Until end of turn, for each nonland card type, you may cast a card of that type from" +
                " among the exiled cards without paying its mana cost.";
    }

    public AminatousAuguryEffect(final AminatousAuguryEffect effect) {
        super(effect);
    }


    @Override
    public AminatousAuguryEffect copy() {
        return new AminatousAuguryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            // move cards from library to exile
            controller.moveCardsToExile(controller.getLibrary().getTopCards(game, 8), source, game, true, source.getSourceId(), CardUtil.createObjectRealtedWindowTitle(source, game, null));
            ExileZone auguryExileZone = game.getExile().getExileZone(source.getSourceId());
            if (auguryExileZone == null) {
                return true;
            }
            Cards cardsToCast = new CardsImpl();
            cardsToCast.addAll(auguryExileZone.getCards(game));
            // put a land card from among them onto the battlefield
            TargetCard target = new TargetCard(
                    Zone.EXILED,
                    StaticFilters.FILTER_CARD_LAND_A
            );
            if (controller.chooseUse(Outcome.PutLandInPlay, "Put a land from among the exiled cards into play?", source, game)) {
                if (controller.choose(Outcome.PutLandInPlay, cardsToCast, target, game)) {
                    Card card = cardsToCast.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cardsToCast.remove(card);
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
            }
            AminatousAuguryExileHandler exileHandler = new AminatousAuguryExileHandler(cardsToCast, source, game);
            for (Card card:cardsToCast.getCards(StaticFilters.FILTER_CARD_NON_LAND,game)) {
                AminatousAuguryCastFromExileEffect effect = new AminatousAuguryCastFromExileEffect(card.getCardType(), exileHandler);
                effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                game.addEffect(effect, source);
            }
        }
        return false;
    }
}

class AminatousAuguryCastFromExileEffect extends AsThoughEffectImpl {
    private final AminatousAuguryExileHandler cardTypeHandler;
    private final EnumSet<CardType> cardType;

    public AminatousAuguryCastFromExileEffect(EnumSet<CardType> cardType, AminatousAuguryExileHandler cardTypeTracker) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.PlayForFree);
        this.cardTypeHandler = cardTypeTracker;
        this.cardType = cardType;
        staticText = "Cast this card without paying its mana cost";
    }

    public AminatousAuguryCastFromExileEffect(final AminatousAuguryCastFromExileEffect effect) {
        super(effect);
        this.cardTypeHandler = effect.cardTypeHandler;
        this.cardType = effect.cardType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AminatousAuguryCastFromExileEffect copy() {
        return new AminatousAuguryCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!cardTypeHandler.atLeastOneAvailable(cardType)){
            return false;
        }
        if (sourceId != null && sourceId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(sourceId);
            if (card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
                Player player = game.getPlayer(affectedControllerId);
                Costs costs = card.getSpellAbility().getCosts().copy();
                costs.add(new ConsumeCardTypeCost(cardType, cardTypeHandler));
                player.setCastSourceIdWithAlternateMana(sourceId, null, costs);
                return true;
            }
        }
        return false;
    }
}



/**
 * Tracks which card types have already been cast, and provides utility functions for confirming used types.
 * (one ExileHandler is shared between all cards from a single cast of Animatou's Augury)
 */
class AminatousAuguryExileHandler {
    private final EnumSet<CardType> usedCardTypes;

    public AminatousAuguryExileHandler(Cards cards, Ability source, Game game){
        usedCardTypes = EnumSet.noneOf(CardType.class);
    }

    public EnumSet<CardType> availableTypes(EnumSet<CardType> types){
        EnumSet<CardType> available = EnumSet.copyOf(types);
        available.removeAll(usedCardTypes);
        return available;
    }

    public boolean atLeastOneAvailable(EnumSet<CardType> types){
        EnumSet<CardType> available = availableTypes(types);
        return !available.isEmpty();
    }

    public boolean useCardType(CardType type){
        if (usedCardTypes.contains(type)){
            return false;
        }
        usedCardTypes.add(type);
        return true;
    }

}

/**
 * Allows the user to choose one of the given card types
 */
class CardTypeChoice extends ChoiceImpl{

    public CardTypeChoice (EnumSet<CardType> types){
        super(false);
        for (CardType type:types){
            this.choices.add(type+"");
        }
        this.message = "Choose card type to cast as";
    }
}

class ConsumeCardTypeCost extends CostImpl{

    final private AminatousAuguryExileHandler exileHandler;
    final private EnumSet<CardType> types;

    public ConsumeCardTypeCost (EnumSet<CardType> types, AminatousAuguryExileHandler exileHandler){
        this.exileHandler = exileHandler;
        this.types = types;
        this.text = "Cast as "+types;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return exileHandler.atLeastOneAvailable(types);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (isPaid()){
            return true;
        }
        CardType choiceType;
        EnumSet<CardType> availableChoices = exileHandler.availableTypes(types);
        if (availableChoices.size()==1){
            // if there is only one possibility, don't need to prompt for a choice
            choiceType = availableChoices.iterator().next();
        }else {
            Choice choice = new CardTypeChoice(availableChoices);
            if (!game.getPlayer(controllerId).choose(Outcome.Neutral, choice, game)) {
                return false;
            }
            Optional<CardType> optionalChoice = Arrays.stream(CardType.values()).filter(type -> type.toString().equals(choice.getChoice())).findAny();
            if (optionalChoice.isPresent()){
                choiceType = optionalChoice.get();
            }else{
                return false;
            }
        }
        paid = exileHandler.useCardType(choiceType);
        return paid;
    }

    @Override
    public Cost copy() {
        return new ConsumeCardTypeCost(types, exileHandler);
    }
}

