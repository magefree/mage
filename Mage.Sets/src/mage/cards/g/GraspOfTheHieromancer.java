
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GraspOfTheHieromancer extends CardImpl {

    public GraspOfTheHieromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature gets +1/+1 and has "Whenever this creature attacks, tap target creature defending player controls."
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        Ability gainedAbility = new GraspOfTheHieromancerTriggeredAbility(new TapTargetEffect(), false);        
        gainedAbility.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("and has \"Whenever this creature attacks, tap target creature defending player controls.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
        
    }
       
    private GraspOfTheHieromancer(final GraspOfTheHieromancer card) {
        super(card);
    }

    @Override
    public GraspOfTheHieromancer copy() {
        return new GraspOfTheHieromancer(this);
    }
}

class GraspOfTheHieromancerTriggeredAbility extends TriggeredAbilityImpl {
    

    
    public GraspOfTheHieromancerTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GraspOfTheHieromancerTriggeredAbility(final GraspOfTheHieromancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getCombat().getAttackers().contains(getSourceId()) ) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            if (defendingPlayerId != null) {                
                this.getTargets().clear();
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
                UUID defenderId = game.getCombat().getDefenderId(getSourceId());
                filter.add(new ControllerIdPredicate(defenderId));
                TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
                this.addTarget(target);
                return true;
            }        
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, tap target creature defending player controls.";
    }

    @Override
    public GraspOfTheHieromancerTriggeredAbility copy() {
        return new GraspOfTheHieromancerTriggeredAbility(this);
    }

}
