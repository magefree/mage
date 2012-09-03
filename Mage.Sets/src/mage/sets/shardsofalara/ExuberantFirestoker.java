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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class ExuberantFirestoker extends CardImpl<ExuberantFirestoker> {

    public ExuberantFirestoker(UUID ownerId) {
        super(ownerId, 99, "Exuberant Firestoker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Human");
        this.subtype.add("Druid");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if you control a creature with power 5 or greater, you may have Exuberant Firestoker deal 2 damage to target player.
        this.addAbility(new ExuberantFirestokerTriggeredAbility());
        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
    }

    public ExuberantFirestoker(final ExuberantFirestoker card) {
        super(card);
    }

    @Override
    public ExuberantFirestoker copy() {
        return new ExuberantFirestoker(this);
    }
}

class ExuberantFirestokerTriggeredAbility extends TriggeredAbilityImpl<ExuberantFirestokerTriggeredAbility> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 5 or greater");

    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 4));
    }

    public ExuberantFirestokerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), true);
        this.addTarget(new TargetPlayer());
    }

    public ExuberantFirestokerTriggeredAbility(final ExuberantFirestokerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ExuberantFirestokerTriggeredAbility copy() {
        return new ExuberantFirestokerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) > 0;
    }

    @Override
    public String getRule() {
        return "At the beginning of your end step, if you control a creature with power 5 or greater, you may have {this} deal 2 damage to target player.";
    }
}
