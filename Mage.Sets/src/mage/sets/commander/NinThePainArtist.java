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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class NinThePainArtist extends CardImpl {

    public NinThePainArtist(UUID ownerId) {
        super(ownerId, 213, "Nin, the Pain Artist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U}{R}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Vedalken");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{U}{R}, {tap}: Nin, the Pain Artist deals X damage to target creature. That creature's controller draws X cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NinThePainArtistEffect(), new ManaCostsImpl<>("{X}{U}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public NinThePainArtist(final NinThePainArtist card) {
        super(card);
    }

    @Override
    public NinThePainArtist copy() {
        return new NinThePainArtist(this);
    }
}

class NinThePainArtistEffect extends OneShotEffect {
    
    NinThePainArtistEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to target creature. That creature's controller draws X cards.";
    }
    
    NinThePainArtistEffect(final NinThePainArtistEffect effect) {
        super(effect);
    }
    
    @Override
    public NinThePainArtistEffect copy() {
        return new NinThePainArtistEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.damage(source.getManaCostsToPay().getX(), source.getSourceId(), game, false, true);
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.drawCards(source.getManaCostsToPay().getX(), game);
            }
            return true;
        }
        return false;
    }
}
