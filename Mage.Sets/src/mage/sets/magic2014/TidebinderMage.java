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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class TidebinderMage extends CardImpl<TidebinderMage> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red or green creature an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public TidebinderMage(UUID ownerId) {
        super(ownerId, 73, "Tidebinder Mage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.expansionSetCode = "M14";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tidebinder Mage enters the battlefield, tap target red or green creature an opponent controls.
        // That creature doesn't untap during its controller's untap step for as long as you control Tidebinder Mage.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new TidebinderMageEffect());
        Target target = new TargetCreaturePermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        this.addWatcher(new TidebinderMageWatcher());

    }

    public TidebinderMage(final TidebinderMage card) {
        super(card);
    }

    @Override
    public TidebinderMage copy() {
        return new TidebinderMage(this);
    }
}

class TidebinderMageEffect extends ReplacementEffectImpl<TidebinderMageEffect> {

    public TidebinderMageEffect() {
        super(Duration.OneUse, Outcome.Detriment);
        this.staticText = "That creature doesn't untap during its controller's untap step for as long as you control {this}";
    }

    public TidebinderMageEffect(final TidebinderMageEffect effect) {
        super(effect);
    }

    @Override
    public TidebinderMageEffect copy() {
        return new TidebinderMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL) {
            if (event.getTargetId().equals(source.getSourceId())) {
                this.used = true;
                return false;
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                this.used = true;
                return false;
            }
        }

        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP) {
            if (event.getTargetId().equals(targetPointer.getFirst(game, source))) {
                return true;
            }
        }

        return false;
    }
}

class TidebinderMageWatcher extends WatcherImpl<TidebinderMageWatcher> {

    TidebinderMageWatcher () {
        super("ControlLost", WatcherScope.CARD);
    }

    TidebinderMageWatcher(TidebinderMageWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL && event.getPlayerId().equals(controllerId) && event.getTargetId().equals(sourceId)) {
            condition = true;
            game.replaceEvent(event);
            return;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(sourceId)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                condition = true;
                game.replaceEvent(event);
                return;
            }
        }
    }

    @Override
    public void reset() {
        //don't reset condition each turn - only when this leaves the battlefield
    }

    @Override
    public TidebinderMageWatcher copy() {
        return new TidebinderMageWatcher(this);
    }
}
