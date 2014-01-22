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
package mage.sets.alliances;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class HelmOfObedience extends CardImpl<HelmOfObedience> {

    public HelmOfObedience(UUID ownerId) {
        super(ownerId, 163, "Helm of Obedience", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "ALL";

        // {X}, {tap}: Target opponent puts cards from the top of his or her library into his or her graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice Helm of Obedience and put that card onto the battlefield under your control. X can't be 0.
        VariableManaCost xCosts = new VariableManaCost();
        xCosts.setMinX(1);
        SimpleActivatedAbility abilitiy = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HelmOfObedienceEffect(), xCosts);
        abilitiy.addCost(new TapSourceCost());
        abilitiy.addTarget(new TargetOpponent(true));
        this.addAbility(abilitiy);
    }

    public HelmOfObedience(final HelmOfObedience card) {
        super(card);
    }

    @Override
    public HelmOfObedience copy() {
        return new HelmOfObedience(this);
    }
}


class HelmOfObedienceEffect extends OneShotEffect<HelmOfObedienceEffect> {


    private static final ManacostVariableValue amount = new ManacostVariableValue();
    
    public HelmOfObedienceEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent puts cards from the top of his or her library into his or her graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice Helm of Obedience and put that card onto the battlefield under your control. X can't be 0";
    }

    public HelmOfObedienceEffect(final HelmOfObedienceEffect effect) {
        super(effect);
    }


    @Override
    public HelmOfObedienceEffect copy() {
        return new HelmOfObedienceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int max = amount.calculate(game, source);
            if(max != 0){
                int numberOfCard = 0;

                while(player.getLibrary().size() > 0) {
                    Card card = player.getLibrary().removeFromTop(game);
                    if (card != null){
                        if(card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false)){
                            if(card.getCardType().contains(CardType.CREATURE)){
                                // If a creature card is put into that graveyard this way, sacrifice Helm of Obedience
                                // and put that card onto the battlefield under your control.
                                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                                if (sourcePermanent != null) {
                                    sourcePermanent.sacrifice(source.getSourceId(), game);
                                }
                                if (game.getState().getZone(card.getId()).equals(Zone.GRAVEYARD)) {
                                    card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId());
                                }
                                break;
                            }
                            else{
                                numberOfCard++;
                                if(numberOfCard >= max){
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
