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
package mage.sets.exodus;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ReturnToHandTargetPermanentCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class ExaltedDragon extends CardImpl {

    public ExaltedDragon(UUID ownerId) {
        super(ownerId, 6, "Exalted Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "EXO";
        this.subtype.add("Dragon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Exalted Dragon can't attack unless you sacrifice a land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExaltedDragonReplacementEffect()));
    }

    public ExaltedDragon(final ExaltedDragon card) {
        super(card);
    }

    @Override
    public ExaltedDragon copy() {
        return new ExaltedDragon(this);
    }
}

class ExaltedDragonReplacementEffect extends ReplacementEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    ExaltedDragonReplacementEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "{this} can't attack unless you sacrifice a land <i>(This cost is paid as attackers are declared.)</i>";
    }

    ExaltedDragonReplacementEffect ( ExaltedDragonReplacementEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if ( player != null ) {
            SacrificeTargetCost attackCost = new SacrificeTargetCost(new TargetControlledPermanent(filter));
            if ( attackCost.canPay(source, source.getSourceId(), event.getPlayerId(), game) &&
                 player.chooseUse(Outcome.Neutral, "Sacrifice a land?", source, game) )
            {
                if (attackCost.pay(source, game, source.getSourceId(), event.getPlayerId(), false) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_ATTACKER;
    }
    

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public ExaltedDragonReplacementEffect copy() {
        return new ExaltedDragonReplacementEffect(this);
    }

}
