package mage.cards.f;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureSpell;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PlotAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;

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

        // If FirestormPhoenix would die, return FirestormPhoenix to its owner's hand instead. Until that player's next turn, that player plays with that card revealed in their hand and can't play it.
        Ability combAbility = new SimpleStaticAbility(new FirestormPhoenixEffect());
        //combAbility.addEffect(new FirestormPhoenixEffect2());
        this.addAbility(combAbility);
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
		super(Duration.WhileOnBattlefield, Outcome.ReturnToHand);
		staticText = "If Firestorm Phoenix would die, return Firestorm Phoenix to its owner's hand instead. Until that player's next turn, that player plays with that card revealed in their hand and can't play it.";
	}
	
	private FirestormPhoenixEffect(final FirestormPhoenixEffect effect) {
		super(effect);
	}
	
	@Override
	public FirestormPhoenixEffect copy() {
		return new FirestormPhoenixEffect(this);
	}
	
	//First attempt to write return to hand effect
	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
	    Player controller = game.getPlayer(source.getControllerId());
	    if (controller != null) {
	        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
	        Cards revealedCards = new CardsImpl();
	        revealedCards.add(game.getCard(((ZoneChangeEvent) event).getTargetId()));
	        if (permanent != null) {
	            controller.moveCards(permanent, Zone.HAND, source, game);
	            game.addEffect(new PlayWithHandRevealedEffect(TargetController.YOU), source);
	            //Reveals specific hand, and only once; need to modify for continuous effect, once I figure that out.
	            //controller.revealCards(source, revealedCards, game);
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && event.getTargetId() == source.getSourceId()) {
        	Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        	if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
        		return true;
        	}
        }
        return false;
    }
}