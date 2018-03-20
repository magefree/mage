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
package mage.cards.b;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author sinsedrix
 */
public class BuildersBane extends CardImpl {

    public BuildersBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Destroy X target artifacts. Builder's Bane deals damage to each player equal to the number of artifacts he or she controlled put into a graveyard this way.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new BuildersBaneEffect());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetArtifactPermanent(xValue, xValue));
        }
    }

    public BuildersBane(final BuildersBane card) {
        super(card);
    }

    @Override
    public BuildersBane copy() {
        return new BuildersBane(this);
    }
}

class BuildersBaneEffect extends OneShotEffect {

    public BuildersBaneEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy X target artifacts. {this} deals damage to each player equal to the number of artifacts he or she controlled put into a graveyard this way";
    }

    public BuildersBaneEffect(final BuildersBaneEffect effect) {
        super(effect);
    }

    @Override
    public BuildersBaneEffect copy() {
        return new BuildersBaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> destroyedArtifactPerPlayer = new HashMap<>();

        // Destroy X target artifacts.
        for (UUID targetID : this.targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetID);
            if (permanent != null) {
                if (permanent.destroy(source.getSourceId(), game, false)) {
                    game.applyEffects();
                    if (permanent.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(permanent.getId())
                            && !game.getState().getZone(permanent.getId()).equals(Zone.GRAVEYARD)) {
                        // A replacement effect has moved the card to another zone as grvayard
                        continue;
                    }
                    destroyedArtifactPerPlayer.merge(permanent.getControllerId(), 1, Integer::sum);
                }
            }
        }

        // Builder's Bane deals damage to each player equal to the number of artifacts he or she controlled put into a graveyard this way.
        for (Map.Entry<UUID, Integer> entry : destroyedArtifactPerPlayer.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player != null) {
                player.damage(entry.getValue(), source.getSourceId(), game, false, true);
            }
        }

        return true;
    }
}
