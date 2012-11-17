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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetStackObject;

/**
 *
 * @author LevelX2
 */
public class ShimmeringGlasskite extends CardImpl<ShimmeringGlasskite> {

    public ShimmeringGlasskite(UUID ownerId) {
        super(ownerId, 51, "Shimmering Glasskite", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setBlue(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shimmering Glasskite becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.
        this.addAbility(new ShimmeringGlasskiteAbility());

    }

    public ShimmeringGlasskite(final ShimmeringGlasskite card) {
        super(card);
    }

    @Override
    public ShimmeringGlasskite copy() {
        return new ShimmeringGlasskite(this);
    }
}

class ShimmeringGlasskiteAbility extends TriggeredAbilityImpl<ShimmeringGlasskiteAbility> {

    protected int turnUsed;

    public ShimmeringGlasskiteAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect(), false);
    }

    public ShimmeringGlasskiteAbility(final ShimmeringGlasskiteAbility ability) {
        super(ability);
        turnUsed = ability.turnUsed;
    }

    @Override
    public ShimmeringGlasskiteAbility copy() {
        return new ShimmeringGlasskiteAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.TARGETED && event.getTargetId().equals(this.getSourceId()) && game.getTurnNum() > turnUsed) {

            this.getTargets().clear();
            TargetStackObject target = new TargetStackObject();
            target.add(event.getSourceId(), game);
            this.addTarget(target);
            turnUsed = game.getTurnNum();
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.";
    }

}
