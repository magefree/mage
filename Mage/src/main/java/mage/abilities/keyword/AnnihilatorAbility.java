package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * 702.84. Annihilator 702.84a Annihilator is a triggered ability. "Annihilator
 * N" means "Whenever this creature attacks, defending player sacrifices N
 * permanents."
 * <p>
 * 702.84b If a creature has multiple instances of annihilator, each triggers
 * separately.
 *
 * @author maurer.it_at_gmail.com
 */
public class AnnihilatorAbility extends TriggeredAbilityImpl {

    String rule;

    public AnnihilatorAbility(int count) {
        super(Zone.BATTLEFIELD, new SacrificeEffect(StaticFilters.FILTER_CONTROLLED_PERMANENTS, count, ""), false);
        this.rule = "Annihilator " + count + " <i>(Whenever this creature attacks, defending player sacrifices "
                + (count == 1 ? "a permanent" : CardUtil.numberToText(count) + " permanents") + ".)</i>";
    }

    protected AnnihilatorAbility(final AnnihilatorAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(sourceId, game);
            if (defendingPlayerId != null) {
                // the id has to be set here because the source can be leave battlefield
                getEffects().setTargetPointer(new FixedTarget(defendingPlayerId));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return rule;
    }

    @Override
    public AnnihilatorAbility copy() {
        return new AnnihilatorAbility(this);
    }

}
