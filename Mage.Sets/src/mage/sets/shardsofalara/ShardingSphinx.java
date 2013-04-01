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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author Plopman
 */
public class ShardingSphinx extends CardImpl<ShardingSphinx> {

    public ShardingSphinx(UUID ownerId) {
        super(ownerId, 55, "Sharding Sphinx", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever an artifact creature you control deals combat damage to a player, you may put a 1/1 blue Thopter artifact creature token with flying onto the battlefield.
        this.addAbility(new ShardingSphinxTriggeredAbility());  
    }

    public ShardingSphinx(final ShardingSphinx card) {
        super(card);
    }

    @Override
    public ShardingSphinx copy() {
        return new ShardingSphinx(this);
    }
}

class ShardingSphinxTriggeredAbility extends TriggeredAbilityImpl<ShardingSphinxTriggeredAbility> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("artifact creature you control");
    static{
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }
    
    public ShardingSphinxTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterToken()), true);
    }

    public ShardingSphinxTriggeredAbility(final ShardingSphinxTriggeredAbility ability) {
            super(ability);
    }

    @Override
    public ShardingSphinxTriggeredAbility copy() {
            return new ShardingSphinxTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER || event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if(permanent != null &&  filter.match(permanent, sourceId, controllerId, game) && ((DamagedEvent) event).isCombatDamage()){
                    return true;
                }
            }
            return false;
    }

    @Override
    public String getRule() {
            return "Whenever a creature you control deals combat damage, " + super.getRule();
    }

}

class ThopterToken extends Token {
    ThopterToken() {
        super("Thopter", "a 1/1 blue Thopter artifact creature token with flying");
        cardType.add(Constants.CardType.CREATURE);
        cardType.add(Constants.CardType.ARTIFACT);
        subtype.add("Thopter");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}