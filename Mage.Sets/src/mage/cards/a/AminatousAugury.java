package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.*;

/**
 *
 * @author credman0
 *
 * Comparing to cards like Narset and Emrakul, the Promised End.
 *
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

class AminatousAuguryEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterLandCard("land card to put on the battlefield");

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
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            //Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
            Set<Card> cards = player.getLibrary().getTopCards(game, 8);




            //Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 8));


            player.moveCards(cards, Zone.EXILED, source, game);




            /*
            if (player.chooseUse(Outcome.PutCardInPlay, "Put a land from among the exiled cards into play?", source, game)) {
                TargetCardInExile target = new TargetCardInExile(StaticFilters.FILTER_CARD_LAND_A);
                if (player.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        return player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                    }
                }
            }

             */
            Set<Card> aminatouCards = new HashSet<>();

            System.out.println("Aminatou Cards Initialized With:" + aminatouCards);

            //Set<UUID> aminatouCardIds = new HashSet<>();

            //EnumSet<CardType> castableCardTypes = EnumSet.noneOf(CardType.class);
            //EnumSet<CardType> usedCardTypes = EnumSet.noneOf(CardType.class);


            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {
                    aminatouCards.add(card);
                }
            }



            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {

                    //aminatouCards.add(card);
                    //aminatouCardIds.add(card.getMainCard().getId());
                    //for ( CardType cType: card.getCardType()) {
                     //   castableCardTypes.add(cType);

                    //}

                    //card.addInfo("ENCHANTMENT", "True", game);

                    ContinuousEffect effect = new AminatousAuguryCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
            }



            //System.out.println(castableCardTypes);

            /*
             * Plan: Figure out active target Ids in applied.
             *  When target is cast, remove other targets of the same type
             *  boolean array for each card type
             *  i.e. was cardType = cast  true? if so, make card castable. If false cannot cast.
             *  only need to send and retrieve boolean array
             */

            //System.out.println("HACK");
            //String testValue = "Sent By Me";



            //game.getState().setValue("castableCardTypes", castableCardTypes);
            System.out.println("Source ID: " + source.getSourceId().toString() + "Cards");
            game.getState().setValue(source.getSourceId().toString() + "Cards", aminatouCards);
            //game.getState().setValue("aminatouCardIds", aminatouCardIds);

            //System.out.println(game.getState().toString());
            //System.out.println(game.getState().getValue(game, player.getId()));

            return true;

            //ContinuousEffect effect = new AminatousAuguryCastFromExileEffect();
            //effect.setTargetPointer(new FixedTargets(cards.getCards(StaticFilters.FILTER_CARD_NON_LAND, game), game));
            //game.addEffect(effect, source);

            //return true;

        }
        return false;

        /*
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            Set<Card> cards = player.getLibrary().getTopCards(game, 8);

            Cards aminatouExileSet = new CardsImpl();
            aminatouExileSet.addAll(cards);

            player.moveCards(cards, Zone.EXILED, source, game);

            TargetCard target = new TargetCard(Zone.EXILED, StaticFilters.FILTER_CARD_LAND);


            //check for land card

            //for (Card xCard : cards){
           //    System.out.println("Card name -- " + xCard.toString());
            //}


            if (aminatouExileSet.count(StaticFilters.FILTER_CARD_LAND, game) > 0) {
                //System.out.println("Exile set contains land!");
                if (player.chooseUse(Outcome.PutCardInPlay, "Put a land from among the exiled cards into play?", source, game)) {
                    if (player.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                                return player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                        }
                    }
                }
            }
            Cards canBeCast = new CardsImpl();
            for (Card card : cards) {
                if (!card.isLand()) {
                    canBeCast.add(card);
                }
            }

            ContinuousEffect effect = new AminatousAuguryCastFromExileEffect();
            effect.setTargetPointer(new FixedTargets(canBeCast, game));
            game.addEffect(effect, source);

            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED) {

                }
            }


            ExileZone auguryExileZone = game.getExile().getExileZone(source.getSourceId());
            Cards cardsToCast = new CardsImpl();
            cardsToCast.addAll(auguryExileZone.getCards(game));



        }

        return false;

         */
    }

    /*
    @Override
    public boolean apply(Game game, Ability source) {
        System.out.println("Aminatou Apply Override");
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
                System.out.println("Card -- " + card.toString());
                AminatousAuguryCastFromExileEffect effect = new AminatousAuguryCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                game.addEffect(effect, source);
            }
        }
        return false;
    }
    */

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

    //track each card type -- 5 types
    //allow casting if not already cast from the 8 cards moved to exile


    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {

        /*
         * pass boolean array for types via game.getState().get/setValue
         *
         * use this information to update fixedtargets
         * if matches enchantment, set .used = true
         *  set effect to used
         * else cast with zero mana
         *
         */


        //Some cards have more than one type i.e. artifact creature, this should count as one for both categories.

        //check which cards have been cast


        //System.out.println("Used Card Types: " + usedCardTypes);


        Set<Card> aminatouCards = (Set<Card>) game.getState().getValue(source.getSourceId().toString() + "Cards");

        //System.out.println("Source ID: " + source.getSourceId().toString() + "Cards");

        Set<CardType> usedCardTypes = new HashSet<>();
        if (game.getState().getValue(source.getSourceId().toString() + "UsedCardTypes") != null){
            usedCardTypes = (Set<CardType>) game.getState().getValue(source.getSourceId().toString() + "UsedCardTypes");
        }

        if (objectId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {

            //Set<Card> aminatouCards = (Set<Card>) game.getState().getValue("aminatouCards");

            Iterator<Card> chkCardCastIterator = aminatouCards.iterator();
            while(chkCardCastIterator.hasNext()){
                Card chkCardCast = chkCardCastIterator.next();

                //System.out.println("checking cards");

                Spell spell = game.getStack().getSpell(chkCardCast.getId());
                if (spell != null) {
                    //System.out.println(chkCardCast.getIdName() + " Was cast -> Removing");
                    usedCardTypes.addAll(chkCardCast.getCardType());
                }
            }

            //System.out.println( "Used Card Types :" + usedCardTypes);
            game.getState().setValue(source.getSourceId().toString() + "UsedCardTypes", usedCardTypes); //update changes
        }

        if (objectId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null && Collections.disjoint(usedCardTypes,card.getCardType())) {
                Player player = game.getPlayer(affectedControllerId);
                if (player != null) {
                    player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
                    return true;
                }
            }
        }
        return false;
    }


    //@Override
    public boolean applies_hack(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {

        //System.out.println("Aminatou Applies Override");

        Player player = game.getPlayer(affectedControllerId);
        EnumSet<CardType> usedCardTypes = EnumSet.noneOf(CardType.class);

        if (game.getState().getValue(source.getSourceId().toString() + "cardTypes") != null) {
            usedCardTypes = (EnumSet<CardType>) game.getState().getValue(source.getSourceId().toString() + "cardTypes");
        }

        for ( CardType xCard : usedCardTypes){
            System.out.println("Used card types -xx- " + xCard.toString());
        }


        //TODO add code for adding additional costs to the card
        if (player != null
                && sourceId != null
                && sourceId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(sourceId);
            if (card != null  && game.getState().getZone(sourceId) == Zone.EXILED) {
                EnumSet<CardType> unusedCardTypes = EnumSet.noneOf(CardType.class);
                for (CardType cardT : card.getCardType()) {
                    System.out.println("Card Types -- " +  cardT.toString());
                    if (!usedCardTypes.contains(cardT)) {
                        unusedCardTypes.add(cardT);
                    }
                }

                for ( CardType xCard : unusedCardTypes){
                    System.out.println("Unused card types -- " + xCard.toString());
                }
                for ( CardType xCard : usedCardTypes){
                    System.out.println("--Used card types -- " + xCard.toString());
                }

                if (!unusedCardTypes.isEmpty()) {
                    if (!game.inCheckPlayableState()) { // some actions may not be done while the game only checks if a card can be cast
                        // Select the card type to consume and remove all not selected card types
                        if (unusedCardTypes.size() > 1) {
                            Choice choice = new ChoiceImpl(true);
                            choice.setMessage("Which card type do you want to consume?");
                            Set<String> choices = choice.getChoices();
                            for (CardType cardType : unusedCardTypes) {
                                choices.add(cardType.toString());
                                System.out.println("Aminatou adds type: ");
                            }
                            player.choose(Outcome.Detriment, choice, game);
                            for (Iterator<CardType> iterator = unusedCardTypes.iterator(); iterator.hasNext();) {
                                CardType next = iterator.next();
                                System.out.println("Aminatou selects: ");
                                if (!next.toString().equals(choice.getChoice())) {
                                    iterator.remove();
                                }
                            }
                            usedCardTypes.add(CardType.fromString(choice.getChoice()));
                        }
                        usedCardTypes.addAll(unusedCardTypes);
                        player.setCastSourceIdWithAlternateMana(sourceId, null, null);
                        game.getState().setValue(source.getSourceId().toString() + "cardTypes", usedCardTypes); //add to list of cast cards
                    }
                    return true;
                }
            }
        }
        return false;
    }


}
