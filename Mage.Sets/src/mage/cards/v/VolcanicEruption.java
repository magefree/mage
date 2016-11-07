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
package mage.cards.v;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class VolcanicEruption extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain", "Mountain");

    public VolcanicEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}{U}");

        // Destroy X target Mountains. Volcanic Eruption deals damage to each creature and each player equal to the number of Mountains put into a graveyard this way.
        this.getSpellAbility().addTarget(new TargetLandPermanent(filter));
        this.getSpellAbility().addEffect(new VolcanicEruptionEffect());
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetLandPermanent(xValue, xValue, filter, false));
        }
    }

    public VolcanicEruption(final VolcanicEruption card) {
        super(card);
    }

    @Override
    public VolcanicEruption copy() {
        return new VolcanicEruption(this);
    }
}

class VolcanicEruptionEffect extends OneShotEffect {

    public VolcanicEruptionEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy X target Mountains. {this} deals damage to each creature and each player equal to the number of Mountains put into a graveyard this way.";
    }

    public VolcanicEruptionEffect(final VolcanicEruptionEffect effect) {
        super(effect);
    }

    @Override
    public VolcanicEruptionEffect copy() {
        return new VolcanicEruptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        int destroyedCount = 0;
        for (UUID targetID : this.targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetID);
            if (permanent != null) {
                if (permanent.destroy(source.getSourceId(), game, false)) {
                    if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                        destroyedCount++;
                    }
                }
            }
        }
        
        if (destroyedCount > 0) {
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
            for (Permanent permanent: permanents) {
                permanent.damage(destroyedCount, source.getSourceId(), game, false, true);
            }
            for (UUID playerId: game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.damage(destroyedCount, source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }
}