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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class Grindstone extends CardImpl<Grindstone> {

    public Grindstone(UUID ownerId) {
        super(ownerId, 280, "Grindstone", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "TMP";

        // {3}, {tap}: Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrindstoneEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
        
        
    }

    public Grindstone(final Grindstone card) {
        super(card);
    }

    @Override
    public Grindstone copy() {
        return new Grindstone(this);
    }
}

class GrindstoneEffect extends OneShotEffect<GrindstoneEffect> {
    
    public GrindstoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player puts the top two cards of his or her library into his or her graveyard. If both cards share a color, repeat this process";
    }
    
    public GrindstoneEffect(final GrindstoneEffect effect) {
        super(effect);
    }
    
    @Override
    public GrindstoneEffect copy() {
        return new GrindstoneEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        boolean colorShared;
        if (targetPlayer != null) {
            do {
                colorShared = false;
                Card card1 = targetPlayer.getLibrary().removeFromTop(game);
                if (card1 != null) {
                    card1.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);    
                    Card card2 = targetPlayer.getLibrary().removeFromTop(game);
                    if (card2 != null) {
                        card2.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                        if (card1.getColor().hasColor() && card2.getColor().hasColor()) {
                            colorShared = card1.getColor().shares(card2.getColor());
                        }
                    }                    
                }

                
            } while (colorShared && targetPlayer.isInGame());
            return true;
        }
        return false;
    }
}
