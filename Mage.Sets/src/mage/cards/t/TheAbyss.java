
package mage.cards.t;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class TheAbyss extends CardImpl {

    public TheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");
        this.supertype.add(SuperType.WORLD);

        // At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of their choice. It can't be regenerated.
        this.addAbility(new TheAbyssTriggeredAbility());
    }

    private TheAbyss(final TheAbyss card) {
        super(card);
    }

    @Override
    public TheAbyss copy() {
        return new TheAbyss(this);
    }
}

class TheAbyssTriggeredAbility extends TriggeredAbilityImpl {

    TheAbyssTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(true), false);
    }

    TheAbyssTriggeredAbility(final TheAbyssTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheAbyssTriggeredAbility copy() {
        return new TheAbyssTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature you control");
            filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
            filter.add(new ControllerIdPredicate(player.getId()));
            Target target = new TargetCreaturePermanent(filter);
            target.setAbilityController(getControllerId());
            target.setTargetController(player.getId());
            this.getTargets().clear();
            this.getTargets().add(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of their choice. It can't be regenerated.";
    }
}
