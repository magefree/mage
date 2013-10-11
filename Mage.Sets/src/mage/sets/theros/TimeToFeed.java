
/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
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
 * creatures won’t fight and neither will deal or be dealt damage. However, you’ll still gain
 * 3 life when the creature you don’t control dies that turn, even if it was the illegal target as Time to Feed resolved.
 * If neither creature is a legal target when Time to Feed tries to resolve, the spell will
 * be countered and none of its effects will happen.
 * If the first target creature dies that turn, you’ll gain 3 life no matter what caused the creature to die or who controls the creature at that time.
 *
 * @author LevelX2
 */
public class TimeToFeed extends CardImpl<TimeToFeed> {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter1.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public TimeToFeed(UUID ownerId) {
        super(ownerId, 181, "Time to Feed", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "THS";

        this.color.setGreen(true);

        // Choose target creature an opponent controls. When that creature dies this turn, you gain 3 life. 
        this.getSpellAbility().addEffect(new TimeToFeedTextEffect());
        // Target creature you control fights that creature.
        Effect effect = new FightTargetsEffect();
        effect.setText("Target creature you control fights that creature");
        this.getSpellAbility().addEffect(effect);

        Target target = new TargetCreaturePermanent(filter1);
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(true));
        
    }

    public TimeToFeed(final TimeToFeed card) {
        super(card);
    }

    @Override
    public TimeToFeed copy() {
        return new TimeToFeed(this);
    }
}

class TimeToFeedTextEffect extends OneShotEffect<TimeToFeedTextEffect> {

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
            DelayedTriggeredAbility ability = new TimeToFeedDiesTriggeredAbility(creature.getId(), creature.getZoneChangeCounter());
            new CreateDelayedTriggeredAbilityEffect(ability, false).apply(game, source);
        }

        return true;
    }
}

class TimeToFeedDiesTriggeredAbility extends DelayedTriggeredAbility<TimeToFeedDiesTriggeredAbility> {

    private UUID watchedCreatureId;
    private int zoneChangeCounter;

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
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            if (event.getTargetId().equals(watchedCreatureId)) {
                Permanent creature = (Permanent) game.getLastKnownInformation(watchedCreatureId, Zone.BATTLEFIELD);
                if (creature.getZoneChangeCounter() == this.zoneChangeCounter) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, " + super.getRule();
    }
}
