package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;


import java.util.*;

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
            Set<Card> cards = player.getLibrary().getTopCards(game, 8);
            player.moveCards(cards, Zone.EXILED, source, game);

            //ExileZone auguryExileZone = game.getExile().get
             //       getExileZone(source.getSourceId());
            //if (auguryExileZone == null) {
            //    return true;
            //}

            //try method from collected conjuring
            Cards aminatouExileGroup = new CardsImpl(cards);

            if(aminatouExileGroup.getCards(StaticFilters.FILTER_CARD_LAND, source.getSourceId(), source.getControllerId(), game).size() > 0 ){
                if (player.chooseUse(Outcome.PutCardInPlay, "Put a land from among exiled cards on to the battlefield?", source, game)) {
                    TargetCard targetLandCard = new TargetCard(1, Zone.EXILED, StaticFilters.FILTER_CARD_LAND);
                    if (player.choose(Outcome.PutCardInPlay, aminatouExileGroup, targetLandCard, game)) {
                        Card landCard = game.getCard(targetLandCard.getFirstTarget());
                        if (landCard == null) {
                            player.moveCards(landCard, Zone.BATTLEFIELD, source, game, false, false, false, null);
                            //game.getState().setValue("PlayFromNotOwnHandZone" + landCard.getId(), Boolean.TRUE);
                            aminatouExileGroup.remove(landCard);
                        }
                    }
                }
            }


            /*
            //you may put a land card from among them onto the battlefield
            TargetCardInExile targetLand = new TargetCardInExile(StaticFilters.FILTER_CARD_LAND_A);
            if (targetLand!=null &&
                    player.chooseUse(Outcome.PutCardInPlay, "Put a land from among exiled cards onto the battlefield?", source, game)) {
                if (player.choose(Outcome.PutCardInPlay, targetLand, source.getSourceId(), game)) {
                    Card card = game.getCard(targetLand.getFirstTarget());
                    if (card != null) {
                        player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                    }
                }
            }
           */

            // Track all non-land cards put into exile with Aminatou
            Set<Card> aminatouCards = new HashSet<>();
            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {
                    aminatouCards.add(card);
                }
            }
            game.getState().setValue(source.getSourceId().toString() + "Cards", aminatouCards);

            //apply effect to each card
            for (Card card : cards) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {
                    ContinuousEffect effect = new AminatousAuguryCastFromExileEffect();
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

        //cards put into exile using Aminatou -- from apply
        Set<Card> aminatouCards = (Set<Card>) game.getState().getValue(source.getSourceId().toString() + "Cards");

        //Set to track which card types from the Aminatou cards have already been cast
        Set<CardType> usedCardTypes = new HashSet<>();
        if (game.getState().getValue(source.getSourceId().toString() + "UsedCardTypes") != null){
            usedCardTypes = (Set<CardType>) game.getState().getValue(source.getSourceId().toString() + "UsedCardTypes");
        }

        if (objectId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {

            // Determine which card types from Aminatou cards were cast
            Iterator<Card> chkCardCastIterator = aminatouCards.iterator();
            while(chkCardCastIterator.hasNext()){
                Card chkCardCast = chkCardCastIterator.next();
                Spell spell = game.getStack().getSpell(chkCardCast.getId());
                if (spell != null) {
                    usedCardTypes.addAll(chkCardCast.getCardType());
                }
            }
            game.getState().setValue(source.getSourceId().toString() + "UsedCardTypes", usedCardTypes); //update changes


            // make all eligible card types zero mana to cast
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
}

