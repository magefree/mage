
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class ThroatSlitter extends CardImpl {

    public ThroatSlitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ninjutsu {2}{B} ({2}{B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{2}{B}"));

        // Whenever Throat Slitter deals combat damage to a player, destroy target nonblack creature that player controls.
        this.addAbility(new ThroatSlitterTriggeredAbility());
    }

    private ThroatSlitter(final ThroatSlitter card) {
        super(card);
    }

    @Override
    public ThroatSlitter copy() {
        return new ThroatSlitter(this);
    }
}

class ThroatSlitterTriggeredAbility extends TriggeredAbilityImpl {

    ThroatSlitterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    ThroatSlitterTriggeredAbility(final ThroatSlitterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThroatSlitterTriggeredAbility copy() {
        return new ThroatSlitterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage() 
                && event.getSourceId().equals(sourceId)) {

            FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature that player controls");
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
            filter.setMessage("nonblack creature controlled by " + game.getPlayer(event.getTargetId()).getLogName());
            this.getTargets().clear();
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, destroy target nonblack creature that player controls.";
    }
}
