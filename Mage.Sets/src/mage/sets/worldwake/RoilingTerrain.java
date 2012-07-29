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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class RoilingTerrain extends CardImpl<RoilingTerrain> {

    public RoilingTerrain(UUID ownerId) {
        super(ownerId, 88, "Roiling Terrain", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
        this.expansionSetCode = "WWK";

        this.color.setRed(true);

        // Destroy target land, then Roiling Terrain deals damage to that land's controller equal to the number of land cards in that player's graveyard.
        this.getSpellAbility().addEffect(new RoilingTerrainEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public RoilingTerrain(final RoilingTerrain card) {
        super(card);
    }

    @Override
    public RoilingTerrain copy() {
        return new RoilingTerrain(this);
    }
}

class RoilingTerrainEffect extends OneShotEffect<RoilingTerrainEffect> {
    
    private static final FilterCard filter = new FilterCard("lands in graveyard");
    
    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public RoilingTerrainEffect() {
        super(Constants.Outcome.Sacrifice);
        this.staticText = "Destroy target land, then {this} deals damage to that land's controller equal to the number of land cards in that player's graveyard";
    }

    public RoilingTerrainEffect(final RoilingTerrainEffect effect) {
        super(effect);
    }

    @Override
    public RoilingTerrainEffect copy() {
        return new RoilingTerrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedLand = game.getPermanent(source.getFirstTarget());
        if (targetedLand != null) {
            Player controller = game.getPlayer(targetedLand.getControllerId());
            targetedLand.destroy(id, game, true);
            int landsInGraveyard = controller.getGraveyard().count(filter, game);
            controller.damage(landsInGraveyard, id, game, false, true);
            return true;
        }
        return false;
    }
}