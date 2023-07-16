
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Noahsark
 */
public final class GuardianOfTheAges extends CardImpl {
    public GuardianOfTheAges(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.GOLEM);
        
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        
        this.addAbility(DefenderAbility.getInstance());
        //Whenever a creature attacks you or a planeswalker you control, if Guardian of the Ages has defender, it loses defender and gains trample.
        this.addAbility(new GuardianOfTheAgesTriggerAbility());
    }
    
    private GuardianOfTheAges(final GuardianOfTheAges card){
        super(card);
    }
    
    @Override
    public GuardianOfTheAges copy(){
        return new GuardianOfTheAges(this);
    }
}

class GuardianOfTheAgesTriggerAbility extends TriggeredAbilityImpl {
    
    private static final FilterCard filter = new FilterCard("creature");
    static{
        filter.add(Predicates.and(CardType.CREATURE.getPredicate()));
    }
    public GuardianOfTheAgesTriggerAbility(){
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance()));
        this.addEffect(new LoseAbilitySourceEffect(DefenderAbility.getInstance()));
    }
    public GuardianOfTheAgesTriggerAbility(final GuardianOfTheAgesTriggerAbility ability){
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game){
        Permanent creature = game.getPermanent(event.getSourceId());
        return creature != null && filter.match(creature, game)
                && game.getOpponents(this.getControllerId()).contains(creature.getControllerId())
                && game.getPermanent(this.getSourceId()).getAbilities().contains(DefenderAbility.getInstance());
    }
    
    @Override
    public String getRule(){
        return "When a creature attacks you or a planeswalker you control, if {this} has defender, it loses defender and gains trample.";
    }
    
    @Override
    public GuardianOfTheAgesTriggerAbility copy(){
        return new GuardianOfTheAgesTriggerAbility(this);
    }
}
