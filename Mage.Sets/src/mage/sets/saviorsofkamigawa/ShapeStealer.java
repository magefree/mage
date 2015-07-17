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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ShapeStealer extends CardImpl {

    public ShapeStealer(UUID ownerId) {
        super(ownerId, 55, "Shape Stealer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Shapeshifter");
        this.subtype.add("Spirit");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // This ability triggers once for each creature blocked by or blocking Shape Stealer. 
        // If multiple creatures block it, Shape Stealer's power and toughness will change for
        // each one in succession. The first trigger put on the stack will be the last to resolve,
        // so that will set Shape Stealer's final power and toughness.
        
        // Whenever Shape Stealer blocks or becomes blocked by a creature, change Shape Stealer's base power and toughness to that creature's power and toughness until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedByCreatureTriggeredAbility(new ShapeStealerEffect(), false));        
    }

    public ShapeStealer(final ShapeStealer card) {
        super(card);
    }

    @Override
    public ShapeStealer copy() {
        return new ShapeStealer(this);
    }
}

class ShapeStealerEffect extends OneShotEffect {
    
    public ShapeStealerEffect() {
        super(Outcome.Detriment);
        this.staticText = "change {this}'s base power and toughness to that creature's power and toughness until end of turn";
    }
    
    public ShapeStealerEffect(final ShapeStealerEffect effect) {
        super(effect);
    }
    
    @Override
    public ShapeStealerEffect copy() {
        return new ShapeStealerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                ContinuousEffect effect = new SetPowerToughnessSourceEffect(permanent.getPower().getValue(), permanent.getToughness().getValue(), Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
