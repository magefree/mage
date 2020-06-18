package mage.cards.a;

import com.sun.org.apache.xml.internal.utils.StringComparable;
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

            //HACK
            //create game state hash map, to map each mage object to each aminatou's augury card.
            HashMap<UUID, UUID> cardIdToAminatouIdMap = new HashMap <UUID, UUID>();
            if (game.getState().getValue("cardToAminatouIdMap")!=null) {
                cardIdToAminatouIdMap = (HashMap<UUID,UUID>) game.getState().getValue("cardToAminatouIdMap");
            }
            for (Card card : cards) {
                cardIdToAminatouIdMap.put(card.getId(), source.getSourceId());
            }
            System.out.println("HMap: " + cardIdToAminatouIdMap.toString());

            game.getState().setValue("cardToAminatouIdMap", cardIdToAminatouIdMap);


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

            //create list of unused card types
            EnumSet<CardType> unusedCardTypes = EnumSet.allOf(CardType.class);
            unusedCardTypes.remove(CardType.LAND);

            //use unique id in case of multiple Aminatou's Augury cast
            String aminatouId = source.getSourceId().toString() + "_unusedCardTypes";
            game.getState().setValue(aminatouId, unusedCardTypes);
            System.out.println("EXID1: " +  aminatouId);
            System.out.println(unusedCardTypes);

            //apply effect to each card
            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {
                    AsThoughEffect effect = new AminatousAuguryCastFromExileEffect(aminatouId);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class AminatousAuguryCastFromExileEffect extends AsThoughEffectImpl {

    private static String aminatouId;

    public AminatousAuguryCastFromExileEffect(String aminatouId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.PlayForFree);
        this.aminatouId = aminatouId;
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



        if (objectId.equals(getTargetPointer().getFirst(game, source)) && playerId.equals(source.getControllerId())) {



            // make all eligible cards types zero mana cost
            //Card card = game.getCard(objectId);
            Player player = game.getPlayer(playerId);
            if (cardToCheck != null && player !=null ) {
            //if (card != null && !Collections.disjoint(unusedCardTypes,card.getCardType()) ) {

                //System.out.println("E: " + aminatouId);  //This goes to null pointer exception
                //System.out.println("E: " + source.getSourceId());  //This goes to null pointer exception

                //if ( !aminatouId.equals(source.getSourceId().toString() + "_unusedCardTypes") ){
                //    System.out.println("Mismatch ID!");
                //    System.out.println("E: " + aminatouId);  //This goes to null pointer exception
                //    System.out.println("E: " + source.getSourceId().toString() + "_unusedCardTypes");  //This goes to null pointer exception
                //}

                //System.out.println("CardToCheck: "  + cardToCheck.getId().toString());
                //System.out.println("CardToCheck ID for " + cardToCheck.getName() + " from Applies: " + cardToCheck.getId().toString());


                HashMap<UUID,UUID> setLocater = (HashMap<UUID, UUID>) game.getState().getValue("cardToAminatouIdMap");
                String aminatouId = setLocater.get(cardToCheck.getId()).toString() + "_unusedCardTypes";


                //load set that tracks which card types are available to cast
                EnumSet<CardType> unusedCardTypes = (EnumSet<CardType>) game.getState().getValue(aminatouId);

                if (!aminatouId.equals(source.getSourceId().toString() + "_unusedCardTypes")){
                    System.out.println("Mismatch: " + aminatouId + " AND " + source.getSourceId().toString());
                }

                System.out.println("From Applies ID: " + source.getSourceId().toString() + "_unusedCardTypes " + " Castable types: " + unusedCardTypes.toString());
                if (unusedCardTypes == null){
                    return false;  //return false or null on error?
                }




                if (!Collections.disjoint(unusedCardTypes,cardToCheck.getCardType())) {
                        //if (player != null)
                    AminatousAuguryCost aminatouCost = new AminatousAuguryCost();
                    Costs costs = new CostsImpl();
                    costs.add(aminatouCost);
                    costs.addAll(affectedAbility.getCosts());
                    player.setCastSourceIdWithAlternateMana(affectedAbility.getSourceId(), null, costs);
                    return true;
                }
            }
        }
        return false;
    }
}


class AminatousAuguryCost extends CostImpl {

    //private static String aminatouId;

    AminatousAuguryCost() {

        this.text = "for each nonland cardtype, you may cast a card of that type from among the exiled cards without paying its mana cost";
    }


    AminatousAuguryCost(final AminatousAuguryCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {


        //System.out.println("EXID3: " + aminatouId);
        //System.out.println("Ability Name: " + ability.getSourceObject(game).getName());
        //load card types available for castings

        System.out.println("Source ID for " + game.getCard(sourceId).getName() + " from Pay: " + sourceId.toString());

        //load correct unusedCardType game state variable
        HashMap<UUID,UUID> setLocater = (HashMap<UUID, UUID>) game.getState().getValue("cardToAminatouIdMap");
        String aminatouId = setLocater.get(sourceId).toString() + "_unusedCardTypes";

        //System.out.println("EXID3: " + aminatouId);
        //System.out.println("Ability Name: " + ability.getSourceObject(game).getName());



        EnumSet<CardType> unusedCardTypes = (EnumSet<CardType>) game.getState().getValue(aminatouId); //this one isn't changing
        if (unusedCardTypes == null){
            System.out.println("Failed to load cast type list");
            return false;
        } else {
            System.out.println("From Pay ID: " + aminatouId + " Castable types: " + unusedCardTypes.toString());
        }



        Set<CardType> castCardTypes = ability.getSourceObject(game).getCardType();


        if (castCardTypes != null) {
            //count matching available card types
            CardType useType = null;
            int numAvailTypes = 0;
            for (CardType chkType : castCardTypes){
                if (unusedCardTypes.contains(chkType)){
                    numAvailTypes++;
                    useType = chkType;
                }
            }

            //System.out.println("Num avialble types " + numAvailTypes);

            if (numAvailTypes == 0){
                //If no available types, then do not cast.
                paid = false;
            } else if (numAvailTypes == 1){
                // If card has multiple types, and only one is unused,
                // then cast using that type OR if card has only one type
                // and it is available, then cast.
                unusedCardTypes.remove(useType);
                paid = true;
            } else if (numAvailTypes >= 2){
                //If card has multiple types, and more than one has not yet been used,
                //then show dialog to select which type to use for cast.
                AminatousAuguryCardTypeChoice choices = new AminatousAuguryCardTypeChoice(castCardTypes);
                Player controller = game.getPlayer(controllerId);
                if (controller.choose(Outcome.Neutral, choices, game)) {
                    CardType choiceType = CardType.fromString(choices.getChoice());
                    unusedCardTypes.remove(choiceType);
                    paid = true;
                }
            }
        } else {
            System.out.println("CardType returned null");
        }

        //save changes to unused card types
        game.getState().setValue( aminatouId, unusedCardTypes);
        System.out.println("From Exit Pay ID: " + aminatouId + " Castable types: " + unusedCardTypes.toString());

        return paid;
    }


    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public AminatousAuguryCost copy() {
        return new AminatousAuguryCost(this);
    }
}

class AminatousAuguryCardTypeChoice extends ChoiceImpl {

    //20180713 -- notes and rules via Scryfall: https://scryfall.com/card/mb1/285/aminatous-augury#rulings
    /*
     * If a nonland card has multiple types, such as an artifact creature, you
     * may cast it as either of those types. For example, you could cast one
     * artifact creature as your artifact card and another artifact creature
     * as your creature card.
     */

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
