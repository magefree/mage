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
package mage.sets.exodus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Cataclysm extends CardImpl {

    public Cataclysm(UUID ownerId) {
        super(ownerId, 3, "Cataclysm", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        this.expansionSetCode = "EXO";

        // Each player chooses from the permanents he or she controls an artifact, a creature, an enchantment, and a land, then sacrifices the rest.
        this.getSpellAbility().addEffect(new CataclysmEffect());
    }

    public Cataclysm(final Cataclysm card) {
        super(card);
    }

    @Override
    public Cataclysm copy() {
        return new Cataclysm(this);
    }
}

class CataclysmEffect extends OneShotEffect {

    public CataclysmEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Each player chooses from among the permanents he or she controls an artifact, a creature, an enchantment, and a land, then sacrifices the rest";
    }

    public CataclysmEffect(CataclysmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<>();

        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);

            Target target1 = new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent(), true);
            Target target2 = new TargetControlledPermanent(1, 1, new FilterControlledCreaturePermanent(), true);
            Target target3 = new TargetControlledPermanent(1, 1, new FilterControlledEnchantmentPermanent(), true);
            Target target4 = new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent(), true);

            if (target1.canChoose(player.getId(), game)) {
                while (player.canRespond() && !target1.isChosen() && target1.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target1, source, game);
                }
                Permanent artifact = game.getPermanent(target1.getFirstTarget());
                if (artifact != null) {
                    chosen.add(artifact);
                }
                target1.clearChosen();
            }

            if (target2.canChoose(player.getId(), game)) {
                while (player.canRespond() && !target2.isChosen() && target2.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target2, source, game);
                }
                Permanent creature = game.getPermanent(target2.getFirstTarget());
                if (creature != null) {
                    chosen.add(creature);
                }
                target2.clearChosen();
            }

            if (target3.canChoose(player.getId(), game)) {
                while (player.canRespond() && !target3.isChosen() && target3.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target3, source, game);
                }
                Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                if (enchantment != null) {
                    chosen.add(enchantment);
                }
                target3.clearChosen();
            }
            
            if (target4.canChoose(player.getId(), game)) {
                while (player.canRespond() && !target4.isChosen() && target4.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Benefit, target4, source, game);
                }
                Permanent land = game.getPermanent(target4.getFirstTarget());
                if (land != null) {
                    chosen.add(land);
                }
                target4.clearChosen();
            }

        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (!chosen.contains(permanent)) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public CataclysmEffect copy() {
        return new CataclysmEffect(this);
    }
}
