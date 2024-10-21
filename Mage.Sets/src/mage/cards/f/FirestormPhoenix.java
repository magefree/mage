package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;

/**
 *
 * @author RobertFrosty
 */

public final class FirestormPhoenix extends CardImpl {

    public FirestormPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If FirestormPhoenix would die, return FirestormPhoenix to its owner's hand instead. 
        // Until that player's next turn, that player plays with that card revealed in their hand and can't play it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new FirestormPhoenixEffect()));
    }

    private FirestormPhoenix(final FirestormPhoenix card) {
        super(card);
    }

    @Override
    public FirestormPhoenix copy() {
        return new FirestormPhoenix(this);
    }
}

class FirestormPhoenixEffect extends ReplacementEffectImpl {
	
	FirestormPhoenixEffect() {
		super(Duration.Custom, Outcome.ReturnToHand);
		staticText = "If {this} would die, return {this} to its owner's hand instead. Until that player’s next turn, that player plays with that card revealed in their hand and can’t play it.";
	}
	
	private FirestormPhoenixEffect(final FirestormPhoenixEffect effect) {
		super(effect);
	}
	
	@Override
	public FirestormPhoenixEffect copy() {
		return new FirestormPhoenixEffect(this);
	}
	
	// First attempt to write FirestormPhoenix effect
	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
	    Player controller = game.getPlayer(source.getControllerId());
	    if (controller != null) {
	        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
	        Cards revealedCards = new CardsImpl();
	        game.getCard(permanent.getId());
	        revealedCards.add(game.getCard(((ZoneChangeEvent) event).getTargetId()));
	        if (permanent != null) {
	        	
	        	// Create ContinuousRuleModifyingEffect that restricts the casting of this card, until your next turn.
	        	ContinuousEffect effectRestrict = new FirestormPhoenixRestrictEffect(permanent, game);
	            game.addEffect(effectRestrict, source);
	            
	            // Create ContinuousEffectImpl that reveals FirestormPhoenix from hand continually, 
	            // until it moves zone (is not in your hand) or until your next turn, whichever comes first. Need to add custom inActive.
	            // Use game.informPlayers to inform players when the card changes zones.
	            ContinuousEffect effectReveal = new FirestormPhoenixRevealEffect(controller, revealedCards, permanent.getIdName());
	            game.addEffect(effectReveal, source);
	        	
	            // Move this card (FirestormPhoenix) to hand.
	        	controller.moveCards(permanent, Zone.HAND, source, game);
	        
	            return true;
	        }
	    }
	    return false;
	}
	
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }
	
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
    	return source.getSourceId().equals(event.getTargetId()) 
        && ((ZoneChangeEvent) event).isDiesEvent();
    }
}

class FirestormPhoenixRevealEffect extends ContinuousEffectImpl {
	
	private Player controller;
	private Cards revealedCards;
	private String cardName;

	FirestormPhoenixRevealEffect(Player tmp_controller, Cards tmp_revealedCards, String tmp_cardName) {
        super(Duration.UntilYourNextTurn, Outcome.Detriment);
        controller = tmp_controller;
        revealedCards = tmp_revealedCards;
        cardName = tmp_cardName;
        staticText = "Until that player’s next turn, that player plays with that card revealed in their hand and can’t play it";
    }

    private FirestormPhoenixRevealEffect(final FirestormPhoenixRevealEffect effect) {
        super(effect);
        this.controller = effect.controller;
        this.revealedCards = effect.revealedCards;
        this.cardName = effect.cardName;
    }

    @Override
    public FirestormPhoenixRevealEffect copy() {
        return new FirestormPhoenixRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
    	return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
    	for(UUID permID : revealedCards) {
    		if(game.getState().getZone(permID) == Zone.HAND) {
        		controller.revealCards(cardName, revealedCards, game, false);
        	} else {
        		discard();
        	}
    	}
		return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return true;
    }
}

class FirestormPhoenixRestrictEffect extends ContinuousRuleModifyingEffectImpl {
	
	String cardName;
	private MageObjectReference mor;
	
	public FirestormPhoenixRestrictEffect(Permanent permanent, Game game) {
        super(Duration.UntilYourNextTurn, Outcome.Detriment);
        mor = new MageObjectReference(permanent, game);
        staticText = "Until that player’s next turn, that player plays with that card revealed in their hand and can’t play it";
    }

    private FirestormPhoenixRestrictEffect(final FirestormPhoenixRestrictEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public FirestormPhoenixRestrictEffect copy() {
        return new FirestormPhoenixRestrictEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = SpellAbility.getSpellAbilityFromEvent(event, game).getCharacteristics(game);
        return card != null 
        && mor.getSourceId().equals(card.getId());
    }
}