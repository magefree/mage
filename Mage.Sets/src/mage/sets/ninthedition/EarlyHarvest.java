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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public class EarlyHarvest extends CardImpl<EarlyHarvest> {

    public EarlyHarvest(UUID ownerId) {
        super(ownerId, 235, "Early Harvest", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{G}{G}");
        this.expansionSetCode = "9ED";

        this.color.setGreen(true);

        // Target player untaps all basic lands he or she controls.
        this.getSpellAbility().addEffect(new UntapAllLandsTargetEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public EarlyHarvest(final EarlyHarvest card) {
        super(card);
    }

    @Override
    public EarlyHarvest copy() {
        return new EarlyHarvest(this);
    }
}

class UntapAllLandsTargetEffect extends OneShotEffect<UntapAllLandsTargetEffect> {

    public UntapAllLandsTargetEffect() {
        super(Outcome.Untap);
        staticText = "Target player untaps all basic lands he or she controls";
    }

    public UntapAllLandsTargetEffect(final UntapAllLandsTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Permanent land: game.getBattlefield().getAllActivePermanents(new FilterLandPermanent(), player.getId(), game)) {
                land.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllLandsTargetEffect copy() {
        return new UntapAllLandsTargetEffect(this);
    }

}