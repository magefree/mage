/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.common.MorbidWatcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReaperFromTheAbyss extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Demon creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.DEMON)));
    }

    public ReaperFromTheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add("Demon");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new ReaperFromTheAbyssAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public ReaperFromTheAbyss(final ReaperFromTheAbyss card) {
        super(card);
    }

    @Override
    public ReaperFromTheAbyss copy() {
        return new ReaperFromTheAbyss(this);
    }

}

class ReaperFromTheAbyssAbility extends TriggeredAbilityImpl {

    public ReaperFromTheAbyssAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    public ReaperFromTheAbyssAbility(final ReaperFromTheAbyssAbility ability) {
        super(ability);
    }

    @Override
    public ReaperFromTheAbyssAbility copy() {
        return new ReaperFromTheAbyssAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Watcher watcher = game.getState().getWatchers().get(MorbidWatcher.class.getSimpleName());
        return watcher.conditionMet();
    }

    @Override
    public String getRule() {
        return "Morbid - At the beginning of each end step, if a creature died this turn, destroy target non-demon creature.";
    }
}
