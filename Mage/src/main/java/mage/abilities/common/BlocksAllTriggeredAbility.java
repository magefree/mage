package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author DarioBarrabinoDev
 */
public class BlocksAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    public BlocksAllTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public BlocksAllTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public BlocksAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter) {
        this(effect, optional, filter, SetTargetPointer.NONE);
    }

    public BlocksAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + CardUtil.addArticle(filter.getMessage()) + " blocks, ");
    }

    protected BlocksAllTriggeredAbility(final BlocksAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BlocksAllTriggeredAbility copy() {
        return new BlocksAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(permanent, game));
                break;
            case PLAYER:
                getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
        }
        return true;
    }
}
