package mage.game.command.emblems;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class ZarielArchdukeOfAvernusEmblem extends Emblem {

    // −6: You get an emblem with "At the end of the first combat phase on your turn, untap target creature you control. After this phase, there is an additional combat phase."
    public ZarielArchdukeOfAvernusEmblem() {
        super("Emblem Zariel");
        this.getAbilities().add(new ZarielArchdukeOfAvernusEmblemAbility());
    }

    private ZarielArchdukeOfAvernusEmblem(final ZarielArchdukeOfAvernusEmblem card) {
        super(card);
    }

    @Override
    public ZarielArchdukeOfAvernusEmblem copy() {
        return new ZarielArchdukeOfAvernusEmblem(this);
    }
}

class ZarielArchdukeOfAvernusEmblemAbility extends TriggeredAbilityImpl {

    ZarielArchdukeOfAvernusEmblemAbility() {
        super(Zone.COMMAND, new UntapTargetEffect());
        this.addEffect(new AdditionalCombatPhaseEffect());
        this.addTarget(new TargetControlledCreaturePermanent());
    }

    private ZarielArchdukeOfAvernusEmblemAbility(final ZarielArchdukeOfAvernusEmblemAbility ability) {
        super(ability);
    }

    @Override
    public ZarielArchdukeOfAvernusEmblemAbility copy() {
        return new ZarielArchdukeOfAvernusEmblemAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId())
                && game.getTurn().getPhase(TurnPhase.COMBAT).getCount() == 0;
    }

    @Override
    public String getRule() {
        return "At the end of the first combat phase on your turn, untap target creature you control. " +
                "After this phase, there is an additional combat phase.";
    }

}