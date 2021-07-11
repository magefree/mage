package mage.cards.a;

import java.util.EnumSet;
import java.util.Iterator;
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

        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
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
        staticText = "Exile the top eight cards of your library. You may put a land card from among them onto the"
                + " battlefield. Until end of turn, for each nonland card type, you may cast a card of that type from"
                + " among the exiled cards without paying its mana cost.";
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
            for (Card card : cardsToCast.getCards(StaticFilters.FILTER_CARD_NON_LAND, game)) {
                AminatousAuguryCastFromExileEffect effect = new AminatousAuguryCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
            }
        }
        return false;
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
        EnumSet<CardType> usedCardTypes = EnumSet.noneOf(CardType.class);

        if (game.getState().getValue(source.getSourceId().toString() + "cardTypes") != null) {
            usedCardTypes = (EnumSet<CardType>) game.getState().getValue(source.getSourceId().toString() + "cardTypes");
        }
        if (player != null
                && objectId != null
                && objectId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null
                    && game.getState().getZone(objectId) == Zone.EXILED) {
                EnumSet<CardType> unusedCardTypes = EnumSet.noneOf(CardType.class);
                for (CardType cardT : card.getCardType(game)) {
                    if (!usedCardTypes.contains(cardT)) {
                        unusedCardTypes.add(cardT);
                    }
                }
                if (!unusedCardTypes.isEmpty()) {
                    if (!game.inCheckPlayableState()) { // some actions may not be done while the game only checks if a card can be cast
                        // Select the card type to consume and remove all not seleczted card types
                        if (unusedCardTypes.size() > 1) {
                            Choice choice = new ChoiceImpl(true);
                            choice.setMessage("Which card type do you want to consume?");
                            Set<String> choices = choice.getChoices();
                            for (CardType cardType : unusedCardTypes) {
                                choices.add(cardType.toString());
                            }
                            player.choose(Outcome.Detriment, choice, game);
                            for (Iterator<CardType> iterator = unusedCardTypes.iterator(); iterator.hasNext();) {
                                CardType next = iterator.next();
                                if (!next.toString().equals(choice.getChoice())) {
                                    iterator.remove();
                                }
                            }
                            usedCardTypes.add(CardType.fromString(choice.getChoice()));
                        }
                        usedCardTypes.addAll(unusedCardTypes);                        
                        game.getState().setValue(source.getSourceId().toString() + "cardTypes", usedCardTypes);
                    }
                    player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
                    return true;
                }
            }
        }
        return false;
    }
}
