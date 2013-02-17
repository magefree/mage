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

package mage.sets.magic2012;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.watchers.WatcherImpl;

/**
 * @author Loki
 */
public class AegisAngel extends CardImpl<AegisAngel> {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(new AnotherPredicate());
    }

    public AegisAngel(UUID ownerId) {
        super(ownerId, 1, "Aegis Angel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "M12";
        this.subtype.add("Angel");
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Aegis Angel enters the battlefield, another target permanent is indestructible for as long as you control Aegis Angel.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AegisAngelEffect(), false);
        Target target = new TargetPermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);

        this.addWatcher(new AegisAngelWatcher());
    }

    public AegisAngel(final AegisAngel card) {
        super(card);
    }

    @Override
    public AegisAngel copy() {
        return new AegisAngel(this);
    }

}

class AegisAngelEffect extends ReplacementEffectImpl<AegisAngelEffect> {

    public AegisAngelEffect() {
        super(Duration.OneUse, Outcome.Benefit);
        this.staticText = "another target permanent is indestructible for as long as you control {this}";
    }

    public AegisAngelEffect(AegisAngelEffect effect) {
        super(effect);
    }

    @Override
    public AegisAngelEffect copy() {
        return new AegisAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
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
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(source.getSourceId())) {
            this.used = true;
            return false;
        }

        return event.getType().equals(GameEvent.EventType.DESTROY_PERMANENT)
                && this.targetPointer.getTargets(game, source).contains(event.getTargetId());
    }
}

class AegisAngelWatcher extends WatcherImpl<AegisAngelWatcher> {

    AegisAngelWatcher() {
        super("ControlLost", WatcherScope.CARD);
    }

    AegisAngelWatcher(AegisAngelWatcher watcher) {
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
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                condition = true;
                game.replaceEvent(event);
            }
        }
    }

    @Override
    public void reset() {
        //don't reset condition each turn - only when this leaves the battlefield
    }

    @Override
    public AegisAngelWatcher copy() {
        return new AegisAngelWatcher(this);
    }
}
