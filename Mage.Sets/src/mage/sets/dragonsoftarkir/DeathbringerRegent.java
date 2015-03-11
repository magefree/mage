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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class DeathbringerRegent extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");
    
    static {
        filter.add(new AnotherPredicate());
    }

    public DeathbringerRegent(UUID ownerId) {
        super(ownerId, 96, "Deathbringer Regent", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Dragon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Deathbringer Regent enters the battlefield, if you cast it from your hand and there are five or more other creatures on the battlefield, destroy all other creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(new DestroyAllEffect(filter), new DeathbringerRegentCondition()), false), new CastFromHandWatcher());
        
    }

    public DeathbringerRegent(final DeathbringerRegent card) {
        super(card);
    }

    @Override
    public DeathbringerRegent copy() {
        return new DeathbringerRegent(this);
    }
}

class DeathbringerRegentCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applies = false;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Watcher watcher = game.getState().getWatchers().get("CastFromHand", source.getSourceId());
            if (watcher != null && watcher.conditionMet()) {
                applies = true;
            }
        }
        if (applies) {
            applies = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), game).size() >= 6;
        }
        return applies;
    }
    
    @Override
    public String toString() {
        return "you cast it from your hand and there are five or more other creatures on the battlefield";
    }
}