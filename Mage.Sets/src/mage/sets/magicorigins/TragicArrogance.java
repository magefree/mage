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
package mage.sets.magicorigins;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public class TragicArrogance extends CardImpl {

    public TragicArrogance(UUID ownerId) {
        super(ownerId, 38, "Tragic Arrogance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        this.expansionSetCode = "ORI";

        // For each player, you choose from among the permanents that player controls an artifact, a creature, an enchantment, and a planeswalker. Then each player sacrifices all other nonland permanents he or she controls.
        this.getSpellAbility().addEffect(new TragicArroganceffect());
    }

    public TragicArrogance(final TragicArrogance card) {
        super(card);
    }

    @Override
    public TragicArrogance copy() {
        return new TragicArrogance(this);
    }
}

class TragicArroganceffect extends OneShotEffect {

    public TragicArroganceffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, you choose from among the permanents that player controls an artifact, a creature, an enchantment, and a planeswalker. Then each player sacrifices all other nonland permanents he or she controls";
    }

    public TragicArroganceffect(final TragicArroganceffect effect) {
        super(effect);
    }

    @Override
    public TragicArroganceffect copy() {
        return new TragicArroganceffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Permanent> choosenPermanent = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterArtifactPermanent filterArtifactPermanent = new FilterArtifactPermanent("an artifact of " + player.getName());
                    filterArtifactPermanent.add(new ControllerIdPredicate(playerId));
                    Target target1 = new TargetArtifactPermanent(1, 1, filterArtifactPermanent, true);

                    FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent("a creature of " + player.getName());
                    filterCreaturePermanent.add(new ControllerIdPredicate(playerId));
                    Target target2 = new TargetPermanent(1, 1, filterCreaturePermanent, true);

                    FilterEnchantmentPermanent filterEnchantmentPermanent = new FilterEnchantmentPermanent("an enchantment of " + player.getName());
                    filterEnchantmentPermanent.add(new ControllerIdPredicate(playerId));
                    Target target3 = new TargetPermanent(1, 1, filterEnchantmentPermanent, true);

                    FilterPlaneswalkerPermanent filterPlaneswalkerPermanent = new FilterPlaneswalkerPermanent("a planeswalker of " + player.getName());
                    filterPlaneswalkerPermanent.add(new ControllerIdPredicate(playerId));
                    Target target4 = new TargetPermanent(1, 1, filterPlaneswalkerPermanent, true);

                    if (target1.canChoose(controller.getId(), game)) {
                        controller.chooseTarget(Outcome.Benefit, target1, source, game);
                        Permanent artifact = game.getPermanent(target1.getFirstTarget());
                        if (artifact != null) {
                            choosenPermanent.add(artifact);
                        }
                        target1.clearChosen();
                    }

                    if (target2.canChoose(player.getId(), game)) {
                        controller.chooseTarget(Outcome.Benefit, target2, source, game);
                        Permanent creature = game.getPermanent(target2.getFirstTarget());
                        if (creature != null) {
                            choosenPermanent.add(creature);
                        }
                        target2.clearChosen();
                    }

                    if (target3.canChoose(player.getId(), game)) {
                        controller.chooseTarget(Outcome.Benefit, target3, source, game);
                        Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                        if (enchantment != null) {
                            choosenPermanent.add(enchantment);
                        }
                        target3.clearChosen();
                    }

                    if (target4.canChoose(player.getId(), game)) {
                        controller.chooseTarget(Outcome.Benefit, target4, source, game);
                        Permanent planeswalker = game.getPermanent(target4.getFirstTarget());
                        if (planeswalker != null) {
                            choosenPermanent.add(planeswalker);
                        }
                        target4.clearChosen();
                    }
                }
            }
            // Then each player sacrifices all other nonland permanents he or she controls
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterNonlandPermanent(), game)) {
                        if (!choosenPermanent.contains(permanent)) {
                            permanent.sacrifice(playerId, game);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
}
