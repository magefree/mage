



package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetPlayer;

/**
 * @author cbt33 / LevelX2
 */
public final class AegisOfHonor extends CardImpl {

    public AegisOfHonor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

	// {1}: The next time an instant or sorcery spell would deal damage to you this
        //turn, that spell deals that damage to its controller instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AegisOfHonorEffect(), new ManaCostsImpl<>("{1}")));

    }

    private AegisOfHonor(final AegisOfHonor card) {
        super(card);
    }

    @Override
    public AegisOfHonor copy() {
        return new AegisOfHonor(this);

    }

}

class AegisOfHonorEffect extends RedirectionEffect {

    private static final FilterInstantOrSorceryCard instantOrSorceryfilter = new FilterInstantOrSorceryCard();

    public AegisOfHonorEffect() {
        super(Duration.EndOfTurn);
        staticText = "The next time an instant or sorcery spell would deal "
        		+ "damage to you this turn, that spell deals that damage to its controller instead";
    }

    public AegisOfHonorEffect(final AegisOfHonorEffect card) {
        super(card);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }    
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
    	if (event.getTargetId().equals(source.getControllerId())) {    //Checks to see the damage is to Aegis of Honor's controller        
            Spell spell = null;
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject == null) {
               stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
            }
            if (stackObject instanceof Spell) {
                spell = (Spell) stackObject;
            }
            //Checks if damage is from a sorcery or instants
            if (spell != null && instantOrSorceryfilter.match(spell.getCard(), game)) {
                TargetPlayer target = new TargetPlayer();
                target.add(spell.getControllerId(), game);
                redirectTarget = target;
                return true;
            }
    	}
    	return false;
    }

    @Override
    public AegisOfHonorEffect copy() {
        return new AegisOfHonorEffect(this);
    }

 }
