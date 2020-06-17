package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.effects.*;
import mage.cards.*;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;

import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;


import java.util.*;

import static mage.filter.predicate.permanent.ControllerControlsIslandPredicate.filter;

/**
 *
 * @author credman0
 * @author etpalmer63
 *
 */
public class AminatousAugury extends CardImpl {

    public AminatousAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}{U}");

        // Exile the top eight cards of your library. You may put a land card from among them onto the battlefield.
        // Until end of turn, for each nonland card type, you may cast a card of that type from among the exiled cards
        // without paying its mana cost.
        this.getSpellAbility().addEffect(new AminatousAuguryPlayLandAndExileEffect());
        //this.getSpellAbility().addEffect(new AminatousAuguryCastFromExileEffect());

        //Ability ability =
    }

    public AminatousAugury(final AminatousAugury card) {
        super(card);
    }

    @Override
    public AminatousAugury copy() {
        return new AminatousAugury(this);
    }
}

class AminatousAuguryPlayLandAndExileEffect extends OneShotEffect {

    //private static final FilterCard filter = new FilterLandCard("land card to put on the battlefield");

    public AminatousAuguryPlayLandAndExileEffect() {
        super(Outcome.PlayForFree);
        staticText = "Exile the top eight cards of your library. You may put a land card from among them onto the"
                + " battlefield. Until end of turn, for each nonland card type, you may cast a card of that type from"
                + " among the exiled cards without paying its mana cost.";
    }

    public AminatousAuguryPlayLandAndExileEffect(final AminatousAuguryPlayLandAndExileEffect effect) {
        super(effect);
    }

    @Override
    public AminatousAuguryPlayLandAndExileEffect copy() {
        return new AminatousAuguryPlayLandAndExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            //Exile the top eight cards of your library.
            Set<Card> cards = player.getLibrary().getTopCards(game, 8);
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            for (Card card : cards) {
                card.moveToExile(exileId,source.getSourceObject(game).getName(), source.getSourceId(), game);
             }


            //you may put a land card from among them onto the battlefield
            Cards aminatouExileGroup = new CardsImpl(cards);
            if(aminatouExileGroup.getCards(StaticFilters.FILTER_CARD_LAND, source.getSourceId(), source.getControllerId(), game).size() > 0 ){
                if (player.chooseUse(Outcome.PutCardInPlay, "Put a land from among exiled cards on to the battlefield?", source, game)) {
                    TargetCard targetLandCard = new TargetCard(1, Zone.EXILED, StaticFilters.FILTER_CARD_LAND);
                    if (player.choose(Outcome.PutCardInPlay, aminatouExileGroup, targetLandCard, game)) {
                        Card landCard = game.getCard(targetLandCard.getFirstTarget());
                        if (landCard != null) {
                            player.moveCards(landCard, Zone.BATTLEFIELD, source, game, false, false, false, null);
                            aminatouExileGroup.remove(landCard);
                        }
                    }
                }
            }

            //if (cards.size()>1) {
             //  ContinuousRuleModifyingEffect effect = new AminatousAuguryCastFromExileEffect();
            //}

            /*
            // Track all non-land cards put into exile with Aminatou
            Set<Card> aminatouCards = new HashSet<>();
            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {
                    aminatouCards.add(card);
                }
            }
            //use unique id in case of multiple Aminatou's
            //game.getState().setValue(source.getSourceId().toString() + "Cards", aminatouCards);

             */

            //set list of unused card types -- TODO: Use exile zone id for unique identifier
            EnumSet<CardType> unusedCardTypes = EnumSet.allOf(CardType.class);
            unusedCardTypes.remove(CardType.LAND); //untested
            //game.getState().setValue(source.getSourceId().toString() + "_unusedCardTypes", unusedCardTypes);
            game.getState().setValue("AA_unusedCardTypes", unusedCardTypes);
            System.out.println("ID:" + source.getSourceId().toString());

            //apply effect to each card
            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {
                    AsThoughEffect effect = new AminatousAuguryCastFromExileEffectTwo(exileId);
                    //ContinuousRuleModifyingEffect effect = new AminatousAuguryCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}


class AminatousAuguryCastFromExileEffect extends ContinuousRuleModifyingEffectImpl{

    AminatousAuguryCastFromExileEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "for each nonland cardtype, you may cast a card of that type from among the exiled cards without paying its mana cost";
    }

    private AminatousAuguryCastFromExileEffect(final AminatousAuguryCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        System.out.println("Game Event: " + GameEvent.EventType.SPELL_CAST);
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //CardType cardType = ArchonOfValorsReachChoice.getType((String) game.getState().getValue(source.getSourceId().toString() + "_cardtype"));
        // spell is not on the stack yet, so we have to check the card
        Card card = game.getCard(event.getSourceId());
        System.out.println("Card name: " + card.getName());
        if (card.getCardType().size()>1){
            System.out.println("This card has mutliple types");
            //ask which type to cast the card
        }
        //check if card type contains a type already cast. If card type has two types, prompt for choice.
        //return cardType != null && card != null && card.getCardType().contains(cardType);
        return card != null; //&& card.getCardType().contains(cardType);
    }

    @Override
    public AminatousAuguryCastFromExileEffect copy() {
        return new AminatousAuguryCastFromExileEffect(this);
    }

}








class AminatousAuguryCastFromExileEffectTwo extends AsThoughEffectImpl {

    private UUID exileZoneId;

    public AminatousAuguryCastFromExileEffectTwo(UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.PlayForFree);
        exileZoneId = exileId;
        staticText = "Cast this card without paying its mana cost";
    }

    public AminatousAuguryCastFromExileEffectTwo(final AminatousAuguryCastFromExileEffectTwo effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AminatousAuguryCastFromExileEffectTwo copy() {
        return new AminatousAuguryCastFromExileEffectTwo(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return applies(objectId, null, source, game, affectedControllerId);
    }


    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {

        Card cardToCheck = game.getCard(objectId);
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards

        if (!isAbilityAppliedForAlternateCast(cardToCheck, affectedAbility, playerId, source)) {
            return false;
        }

        /*
        //cards put into exile using Aminatou -- created in AminatouAuguryEffect - Apply
        Set<Card> aminatouCards = (Set<Card>) game.getState().getValue(source.getSourceId().toString() + "Cards");

        //Set to track which card types from the Aminatou cards have already been cast
        Set<CardType> usedCardTypes = new HashSet<>();
        if (game.getState().getValue(source.getSourceId().toString() + "UsedCardTypes") != null){
            usedCardTypes = (Set<CardType>) game.getState().getValue(source.getSourceId().toString() + "UsedCardTypes");
        }
        */

        // c

        EnumSet<CardType> unusedCardTypes = (EnumSet<CardType>) game.getState().getValue("AA_unusedCardTypes" );
        if (unusedCardTypes == null){
            return false;
        }



        if (objectId.equals(getTargetPointer().getFirst(game, source))
                && playerId.equals(source.getControllerId())) {

            // Determine which card types from Aminatou cards were cast
            /*
            Iterator<Card> chkCardCastIterator = aminatouCards.iterator();
            while(chkCardCastIterator.hasNext()){
                Card chkCardCast = chkCardCastIterator.next();
                //check if spell was cast
                Spell spell = game.getStack().getSpell(chkCardCast.getId());
                if (spell != null) {
                    usedCardTypes.addAll(chkCardCast.getCardType());
                }
            }
            //save changes to used card types
            game.getState().setValue(source.getSourceId().toString() + "UsedCardTypes", usedCardTypes);


             */

            // make all eligible cards types zero mana cost
            Card card = game.getCard(objectId);

            //if ( unusedCardTypes.contains(card.getCardType())){
                //allow casting
            //}

            //if (card != null && Collections.disjoint(usedCardTypes,card.getCardType())) {
            if (card != null && !Collections.disjoint(unusedCardTypes,card.getCardType()) ) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {

                        //PayLifeCost cost = new PayLifeCost(affectedAbility.getManaCosts().convertedManaCost());
                        AminatousAuguryCost cost = new AminatousAuguryCost(exileZoneId);
                        Costs costs = new CostsImpl();
                        costs.add(cost);
                        costs.addAll(affectedAbility.getCosts());
                        //controller.setCastSourceIdWithAlternateMana(affectedAbility.getSourceId(), null, costs);

                        //player.setCastSourceIdWithAlternateMana(affectedAbility.getSourceId(), null, affectedAbility.getCosts());
                        player.setCastSourceIdWithAlternateMana(affectedAbility.getSourceId(), null, costs);

                        return true;
                    }

            }
        }
        return false;
    }
}


class AminatousAuguryCost extends CostImpl {

    private UUID exileZoneId;

    AminatousAuguryCost(UUID exileId) {
        //take card type as input, if card has more that one type, need to choose it here.
        //
        exileZoneId = exileId;

        //this.addTarget(sourceInExile.getSourceId());
        //give one of each type to spend, remove as types are cast
        this.text = "for each nonland cardtype, you may cast a card of that type from among the exiled cards without paying its mana cost";
    }


    AminatousAuguryCost(final AminatousAuguryCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {

        //available card types to cast
        System.out.println("ID:" + sourceId.toString());
        System.out.println("Name: " + ability.getSourceObject(game).getName());
        //EnumSet<CardType> unusedCardTypes = (EnumSet<CardType>) game.getState().getValue(sourceId.toString() + "_unusedCardTypes");
        EnumSet<CardType> unusedCardTypes = (EnumSet<CardType>) game.getState().getValue("AA_unusedCardTypes");
        if (unusedCardTypes == null){
            System.out.println("Failed to load cast type list");
            return false;
        } else {
            System.out.println("Castable types: " + unusedCardTypes.toString());
        }

        // if card has type that matches a type on the unused list, allow casting
        // at least one element in common.


        //Player controller = game.getPlayer(source.getControllerId());
        Player controller = game.getPlayer(controllerId);

        Set<CardType> cardTypes = ability.getSourceObject(game).getCardType();


        if (cardTypes != null) {
            //for card with multiple types allow selection of type
            //when both types are available

            CardType useType = null;
            int numAvailTypes = 0;
            for (CardType chkType : cardTypes){
                if (unusedCardTypes.contains(chkType)){
                    numAvailTypes++;
                    useType = chkType;
                }
            }

            System.out.println("Num avialble types " + numAvailTypes);

            if (numAvailTypes == 0){
                paid = false;
            } else if (numAvailTypes == 1){
                unusedCardTypes.remove(useType);
                paid = true;
            } else if (numAvailTypes >= 2){
                //choose type
                AminatousAuguryCardTypeChoice choices = new AminatousAuguryCardTypeChoice(cardTypes);
                if (controller.choose(Outcome.Neutral, choices, game)) {
                    CardType choiceType = CardType.fromString(choices.getChoice());
                    unusedCardTypes.remove(choiceType);
                    paid = true;
                }
            }


            /*
            if ( unusedCardTypes.containsAll(cardTypes) && cardTypes.size() > 1) {
                AminatousAuguryCardTypeChoice choices = new AminatousAuguryCardTypeChoice(cardTypes);
                if (controller.choose(Outcome.Neutral, choices, game)) {
                    CardType choiceType = CardType.fromString(choices.getChoice());
                    cardTypes.clear();
                    cardTypes.add(choiceType);
                }
            } else {
                for (CardType chkType : cardTypes){
                    if (unusedCardTypes.contains(chkType)){

                    }
                }
            }
            */

            /*
            for (CardType chkType : cardTypes){
                System.out.println("chkType: " + chkType.toString());
                if (unusedCardTypes.contains(chkType)){
                    unusedCardTypes.remove(chkType);
                    paid = true;
                }
                break;
            }

             */

        } else {
            System.out.println("CardType returned null");
        }

        //save changes to used card types
        game.getState().setValue( "AA_unusedCardTypes", unusedCardTypes);
        System.out.println("Castable types: " + unusedCardTypes.toString());

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {

        //System.out.println( "EZID: " + game.getExile().getExileZone(exileZoneId));
        //System.out.println( "S ID: " + game.getState().getZone(sourceId));
        return targets.canChoose(controllerId, game);

    }

    @Override
    public AminatousAuguryCost copy() {
        return new AminatousAuguryCost(this);
    }
}

class AminatousAuguryCardTypeChoice extends ChoiceImpl {

    AminatousAuguryCardTypeChoice(Set<CardType> selectFromTypes) {
        super(true);

        for (CardType cType : selectFromTypes ){
            this.choices.add(cType.toString());
        }
        this.message = "Choose a single type for casting";
    }

    private AminatousAuguryCardTypeChoice(final AminatousAuguryCardTypeChoice choice) {
        super(choice);
    }

    @Override
    public AminatousAuguryCardTypeChoice copy() {
        return new AminatousAuguryCardTypeChoice(this);
    }

}
