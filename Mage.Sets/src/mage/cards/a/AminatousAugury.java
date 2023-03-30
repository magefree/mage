package mage.cards.a;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author credman0
 */
public class AminatousAugury extends CardImpl {

    public AminatousAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}{U}");

        // Exile the top eight cards of your library.
        // You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a card of that type from among the exiled cards
        // without paying its mana cost.
        this.getSpellAbility().addEffect(new AminatousAuguryEffect());
    }

    private AminatousAugury(final AminatousAugury card) {
        super(card);
    }

    @Override
    public AminatousAugury copy() {
        return new AminatousAugury(this);
    }

}

class AminatousAuguryEffect extends OneShotEffect {

    public AminatousAuguryEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile the top eight cards of your library. "
                + "You may put a land card from among them onto the battlefield. "
                + "Until end of turn, for each nonland card type, "
                + "you may cast a card of that type from among the exiled cards without paying its mana cost.";
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
        if (controller == null) {
            return false;
        }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }

        // move cards from library to exile
        controller.moveCardsToExile(controller.getLibrary().getTopCards(game, 8), source, game, true, source.getSourceId(), CardUtil.createObjectRealtedWindowTitle(source, game, null));
        ExileZone auguryExileZone = game.getExile().getExileZone(source.getSourceId());
        if (auguryExileZone == null) {
            return true;
        }

        Cards cardsToCast = new CardsImpl();
        cardsToCast.addAll(auguryExileZone.getCards(game));

        // put a land card from among them onto the battlefield
        TargetCard target = new TargetCard(Zone.EXILED, StaticFilters.FILTER_CARD_LAND_A);

        if (cardsToCast.count(StaticFilters.FILTER_CARD_LAND, game) > 0) {
            if (controller.chooseUse(Outcome.PutLandInPlay, "Put a land from among the exiled cards into play?", source, game)) {
                if (controller.choose(Outcome.PutLandInPlay, cardsToCast, target, game)) {
                    Card card = cardsToCast.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cardsToCast.remove(card);
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
            }
        }

        // TODO staticFilters must be configured to check the main card face (Ex: MDFC card like Sea Gate Restoration does not count as a land if face up)
        for (Card card : cardsToCast.getCards(game)) {
            // ex: Sea Gate Restoration bug #9956
            if (card instanceof ModalDoubleFacesCard) {
                ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                if (leftHalfCard != null
                        && !leftHalfCard.isLand(game)) {
                    AminatousAuguryCastFromExileEffect effect = new AminatousAuguryCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(leftHalfCard, game));
                    game.addEffect(effect, source);
                }
                continue;
            }
            if (!card.isLand(game)) {
                AminatousAuguryCastFromExileEffect effect = new AminatousAuguryCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}

class AminatousAuguryCastFromExileEffect extends AsThoughEffectImpl {

    public AminatousAuguryCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.PlayForFree);
        staticText = "Cast this card without paying its mana cost";
    }

    public AminatousAuguryCastFromExileEffect(final AminatousAuguryCastFromExileEffect effect) {
        super(effect);
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
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }

        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        EnumSet<CardType> usedCardTypes;
        if (game.getState().getValue(source.getSourceId().toString() + "cardTypes") == null) {
            // The effect has not been applied fully yet, so there are no previously cast times
            usedCardTypes = EnumSet.noneOf(CardType.class);
        } else {
            usedCardTypes = (EnumSet<CardType>) game.getState().getValue(source.getSourceId().toString() + "cardTypes");
        }

        if (objectId == null || !objectId.equals(getTargetPointer().getFirst(game, source))) {
            return false;
        }

        if (game.getState().getZone(objectId) != Zone.EXILED) {
            return false;
        }

        Card card = game.getCard(objectId);
        if (card == null) {
            return false;
        }

        // Figure out which of the current card's types have not been cast before
        EnumSet<CardType> unusedCardTypes = EnumSet.noneOf(CardType.class);
        for (CardType cardT : card.getCardType(game)) {
            if (!usedCardTypes.contains(cardT)) {
                unusedCardTypes.add(cardT);
            }
        }

        // The current card has only card types that have been cast before, so it can't be cast
        if (unusedCardTypes.isEmpty()) {
            return false;
        }

        // some actions may not be done while the game only checks if a card can be cast
        if (!game.inCheckPlayableState()) {
            // Select the card type to consume and remove all not selected card types
            if (unusedCardTypes.size() > 1) {
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Which card type do you want to consume?");
                Set<String> choices = choice.getChoices();
                for (CardType cardType : unusedCardTypes) {
                    choices.add(cardType.toString());
                }
                player.choose(Outcome.Detriment, choice, game);
                unusedCardTypes.removeIf(next -> !next.toString().equals(choice.getChoice()));
                usedCardTypes.add(CardType.fromString(choice.getChoice()));
            }
            usedCardTypes.addAll(unusedCardTypes);
            game.getState().setValue(source.getSourceId().toString() + "cardTypes", usedCardTypes);
        }
        player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
        return true;
    }
}
