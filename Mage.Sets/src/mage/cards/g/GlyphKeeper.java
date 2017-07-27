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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.NumberOfTimesPermanentTargetedATurnWatcher;

/**
 *
 * @author Styxo
 */
public class GlyphKeeper extends CardImpl {

    public GlyphKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add("Sphinx");
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Glyph Keeper becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.
        this.addAbility(new GlyphKeeperAbility(), new NumberOfTimesPermanentTargetedATurnWatcher());

        // Embalm {5}{U}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl("{5}{U}{U}"), this));

    }

    public GlyphKeeper(final GlyphKeeper card) {
        super(card);
    }

    @Override
    public GlyphKeeper copy() {
        return new GlyphKeeper(this);
    }
}

class GlyphKeeperAbility extends TriggeredAbilityImpl {

    public GlyphKeeperAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    public GlyphKeeperAbility(final GlyphKeeperAbility ability) {
        super(ability);
    }

    @Override
    public GlyphKeeperAbility copy() {
        return new GlyphKeeperAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature()) {
                NumberOfTimesPermanentTargetedATurnWatcher watcher = (NumberOfTimesPermanentTargetedATurnWatcher) game.getState().getWatchers().get(NumberOfTimesPermanentTargetedATurnWatcher.class.getSimpleName());
                if (watcher != null
                        && watcher.notMoreThanOnceTargetedThisTurn(permanent, game)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.";
    }

}