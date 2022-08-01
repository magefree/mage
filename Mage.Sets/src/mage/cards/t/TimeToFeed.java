

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * Time to Feed has two targets: a creature an opponent controls and a creature you control.
 * If only one of those creatures is a legal target when Time to Feed tries to resolve, the
 * creatures won't fight and neither will deal or be dealt damage. However, you'll still gain
 * 3 life when the creature you don't control dies that turn, even if it was the illegal target as Time to Feed resolved.
 * If neither creature is a legal target when Time to Feed tries to resolve, the spell will
 * be countered and none of its effects will happen.
 * If the first target creature dies that turn, you'll gain 3 life no matter what caused the creature to die or who controls the creature at that time.
 *
 * @author LevelX2
 */
public final class TimeToFeed extends CardImpl {

    public TimeToFeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Choose target creature an opponent controls. When that creature dies this turn, you gain 3 life. 
        this.getSpellAbility().addEffect(new TimeToFeedTextEffect());
        // Target creature you control fights that creature.
        Effect effect = new FightTargetsEffect();
        effect.setText("Target creature you control fights that creature. " +
                "<i>(Each deals damage equal to its power to the other.)</i>");
        this.getSpellAbility().addEffect(effect);

        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        
    }

    private TimeToFeed(final TimeToFeed card) {
        super(card);
    }

    @Override
    public TimeToFeed copy() {
        return new TimeToFeed(this);
    }
}

class TimeToFeedTextEffect extends OneShotEffect {

    public TimeToFeedTextEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose target creature an opponent controls. When that creature dies this turn, you gain 3 life";
    }

    public TimeToFeedTextEffect(final TimeToFeedTextEffect effect) {
        super(effect);
    }

    @Override
    public TimeToFeedTextEffect copy() {
        return new TimeToFeedTextEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature != null) {
            DelayedTriggeredAbility ability = new TimeToFeedDiesTriggeredAbility(creature.getId(), creature.getZoneChangeCounter(game));
            new CreateDelayedTriggeredAbilityEffect(ability, false).apply(game, source);
        }

        return true;
    }
}

class TimeToFeedDiesTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID watchedCreatureId;
    private final int zoneChangeCounter;

    public TimeToFeedDiesTriggeredAbility(UUID watchedCreatureId, int zoneChangeCounter) {
        super(new GainLifeEffect(3), Duration.EndOfTurn, false);
        this.watchedCreatureId = watchedCreatureId;
        this.zoneChangeCounter = zoneChangeCounter;
    }

    public TimeToFeedDiesTriggeredAbility(final TimeToFeedDiesTriggeredAbility ability) {
        super(ability);
        this.watchedCreatureId = ability.watchedCreatureId;
        this.zoneChangeCounter = ability.zoneChangeCounter;
    }

    @Override
    public TimeToFeedDiesTriggeredAbility copy() {
        return new TimeToFeedDiesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            if (event.getTargetId().equals(watchedCreatureId)) {
                Permanent creature = (Permanent) game.getLastKnownInformation(watchedCreatureId, Zone.BATTLEFIELD);
                if (creature.getZoneChangeCounter(game) == this.zoneChangeCounter) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When that creature dies this turn, " ;
    }
}
