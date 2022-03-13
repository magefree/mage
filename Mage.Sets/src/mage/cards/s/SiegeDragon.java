
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public final class SiegeDragon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Walls your opponents control");
    
    static {
        filter.add(SubType.WALL.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }
    
    public SiegeDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Siege Dragon enters the battlefield, destroy all Walls your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter), false));
        
        // Whenever Siege Dragon attacks, if defending player controls no Walls, it deals 2 damage to each creature without flying that player controls.
        this.addAbility(new SiegeDragonAttacksTriggeredAbility());
    }

    private SiegeDragon(final SiegeDragon card) {
        super(card);
    }

    @Override
    public SiegeDragon copy() {
        return new SiegeDragon(this);
    }
}

class SiegeDragonAttacksTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("wall");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public SiegeDragonAttacksTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SiegeDragonDamageEffect());
    }

    public SiegeDragonAttacksTriggeredAbility(final SiegeDragonAttacksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SiegeDragonAttacksTriggeredAbility copy() {
        return new SiegeDragonAttacksTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
        return defendingPlayerId != null && game.getBattlefield().countAll(filter, defendingPlayerId, game) < 1;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, if defending player controls no Walls, it deals 2 damage to each creature without flying that player controls.";
    }
}

class SiegeDragonDamageEffect extends OneShotEffect {

    public SiegeDragonDamageEffect() {
        super(Outcome.Damage);
    }
    
    public SiegeDragonDamageEffect(final SiegeDragonDamageEffect effect) {
        super(effect);
    }

    @Override
    public SiegeDragonDamageEffect copy() {
        return new SiegeDragonDamageEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        if (defendingPlayerId != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(defendingPlayerId));
            filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
            for (Permanent permanent : permanents) {
                permanent.damage(2, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
    
}
