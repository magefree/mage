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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public class Whipkeeper extends CardImpl<Whipkeeper> {

    public Whipkeeper(UUID ownerId) {
        super(ownerId, 228, "Whipkeeper", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Dwarf");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Whipkeeper deals damage to target creature equal to the damage already dealt to it this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WhipkeeperEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        
    }

    public Whipkeeper(final Whipkeeper card) {
        super(card);
    }

    @Override
    public Whipkeeper copy() {
        return new Whipkeeper(this);
    }
}

class WhipkeeperEffect extends OneShotEffect<WhipkeeperEffect> {

    public WhipkeeperEffect() {
        super(Outcome.Damage);
        staticText = "{tap}: {this} deals damage to target creature equal to the damage already dealt to it this turn.";
    }
    public WhipkeeperEffect(final WhipkeeperEffect effect) {
        super(effect);
    }
    
    @Override
    public WhipkeeperEffect copy() {
        return new WhipkeeperEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            creature.damage(creature.getDamage(), source.getSourceId(), game, true, false);
            return true;
        }
        return false;
    }
    
}