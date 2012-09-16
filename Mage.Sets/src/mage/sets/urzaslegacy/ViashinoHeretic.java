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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Plopman
 */
public class ViashinoHeretic extends CardImpl<ViashinoHeretic> {

    public ViashinoHeretic(UUID ownerId) {
        super(ownerId, 95, "Viashino Heretic", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ULG";
        this.subtype.add("Viashino");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}{R}, {tap}: Destroy target artifact. Viashino Heretic deals damage to that artifact's controller equal to the artifact's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ViashinoHereticEffect(), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    public ViashinoHeretic(final ViashinoHeretic card) {
        super(card);
    }

    @Override
    public ViashinoHeretic copy() {
        return new ViashinoHeretic(this);
    }
}

class ViashinoHereticEffect extends OneShotEffect<ViashinoHereticEffect> {

    
    public ViashinoHereticEffect() {
        super(Constants.Outcome.DestroyPermanent);
    }

    public ViashinoHereticEffect(final ViashinoHereticEffect effect) {
        super(effect);
    }

    @Override
    public ViashinoHereticEffect copy() {
        return new ViashinoHereticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if(permanent != null)
        {
            int couvertedManaCost = permanent.getManaCost().convertedManaCost();
            Player player = game.getPlayer(permanent.getControllerId());
            permanent.destroy(source.getId(), game, false);
            if(player != null){
                player.damage(couvertedManaCost, source.getId(), game, false, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Destroy target artifact. Viashino Heretic deals damage to that artifact's controller equal to the artifact's converted mana cost";
    }

}