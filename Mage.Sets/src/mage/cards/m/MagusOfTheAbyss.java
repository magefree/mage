
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
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
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MagusOfTheAbyss extends CardImpl {

    public MagusOfTheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // At the beginning of each player's upkeep, destroy target nonartifact creature that player controls of their choice. It can't be regenerated.
        this.addAbility(new MagusOfTheAbyssTriggeredAbility());
    }

    private MagusOfTheAbyss(final MagusOfTheAbyss card) {
        super(card);
    }

    @Override
    public MagusOfTheAbyss copy() {
        return new MagusOfTheAbyss(this);
    }
}

class MagusOfTheAbyssTriggeredAbility extends TriggeredAbilityImpl {

    MagusOfTheAbyssTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(true), false);
    }

    private MagusOfTheAbyssTriggeredAbility(final MagusOfTheAbyssTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MagusOfTheAbyssTriggeredAbility copy() {
        return new MagusOfTheAbyssTriggeredAbility(this);
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