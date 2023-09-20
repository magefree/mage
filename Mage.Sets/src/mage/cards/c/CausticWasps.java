
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author Mitchel Stein
 */
public final class CausticWasps extends CardImpl {

    public CausticWasps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Caustic Wasps deals combat damage to a player, you may destroy target artifact that player controls.
        this.addAbility(new CausticWaspsTriggeredAbility());
    }

    private CausticWasps(final CausticWasps card) {
        super(card);
    }

    @Override
    public CausticWasps copy() {
        return new CausticWasps(this);
    }
}

class CausticWaspsTriggeredAbility extends TriggeredAbilityImpl {

    public CausticWaspsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), true);
    }

    private CausticWaspsTriggeredAbility(final CausticWaspsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CausticWaspsTriggeredAbility copy() {
        return new CausticWaspsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Player player = game.getPlayer(event.getTargetId());
            if (player != null) {
                FilterPermanent filter = new FilterPermanent("an artifact controlled by " + player.getLogName());
                filter.add(CardType.ARTIFACT.getPredicate());
                filter.add(new ControllerIdPredicate(event.getTargetId()));

                this.getTargets().clear();
                this.addTarget(new TargetPermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may destroy target artifact that player controls.";
    }
}
