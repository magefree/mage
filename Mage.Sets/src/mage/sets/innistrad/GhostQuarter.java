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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public class GhostQuarter extends CardImpl<GhostQuarter> {

    public GhostQuarter(UUID ownerId) {
        super(ownerId, 240, "Ghost Quarter", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ISD";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {tap}, Sacrifice Ghost Quarter: Destroy target land. Its controller may search his or her library for a basic land card, put it onto the battlefield, then shuffle his or her library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetLandPermanent());
        ability.addEffect(new GhostQuarterEffect());
        this.addAbility(ability);
    }

    public GhostQuarter(final GhostQuarter card) {
        super(card);
    }

    @Override
    public GhostQuarter copy() {
        return new GhostQuarter(this);
    }
}

class GhostQuarterEffect extends OneShotEffect<GhostQuarterEffect> {

    public GhostQuarterEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Its controller may search his or her library for a basic land card, put it onto the battlefield, then shuffle his or her library";
    }

    public GhostQuarterEffect(final GhostQuarterEffect effect) {
        super(effect);
    }

    @Override
    public GhostQuarterEffect copy() {
        return new GhostQuarterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player.chooseUse(Outcome.PutLandInPlay, "Do you wish to search for a basic land, put it onto the battlefied and then shuffle your library?", game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
                if (player.searchLibrary(target, game)) {
                    Card card = player.getLibrary().remove(target.getFirstTarget(), game);
                    if (card != null) {
                        card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                    }
                }
                player.shuffleLibrary(game);
            }
            return true;
        }
        return false;
    }
}
