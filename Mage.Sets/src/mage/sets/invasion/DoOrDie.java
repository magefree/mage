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
package mage.sets.invasion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class DoOrDie extends CardImpl {

    public DoOrDie(UUID ownerId) {
        super(ownerId, 102, "Do or Die", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "INV";

        // Separate all creatures target player controls into two piles. Destroy all creatures in the pile of that player's choice. They can't be regenerated.
        this.getSpellAbility().addEffect(new DoOrDieEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public DoOrDie(final DoOrDie card) {
        super(card);
    }

    @Override
    public DoOrDie copy() {
        return new DoOrDie(this);
    }
}

class DoOrDieEffect extends OneShotEffect {

    public DoOrDieEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Separate all creatures target player controls into two piles. Destroy all creatures in the pile of that player's choice. They can't be regenerated";
    }

    public DoOrDieEffect(final DoOrDieEffect effect) {
        super(effect);
    }

    @Override
    public DoOrDieEffect copy() {
        return new DoOrDieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            int count = game.getBattlefield().countAll(new FilterCreaturePermanent(), targetPlayer.getId(), game);
            TargetCreaturePermanent creatures = new TargetCreaturePermanent(0, count, new FilterCreaturePermanent("creatures to put in the first pile"), true);
            List<Permanent> pile1 = new ArrayList<>();
            creatures.setRequired(false);
            if (player.choose(Outcome.Neutral, creatures, source.getSourceId(), game)) {
                List<UUID> targets = creatures.getTargets();
                for (UUID targetId : targets) {
                    Permanent p = game.getPermanent(targetId);
                    if (p != null) {
                        pile1.add(p);
                    }
                }
            }
            List<Permanent> pile2 = new ArrayList<>();
            for (Permanent p: game.getBattlefield().getAllActivePermanents(targetPlayer.getId())) {
                if (!pile1.contains(p)) {
                    pile2.add(p);
                }
            }

            boolean choice = targetPlayer.choosePile(Outcome.DestroyPermanent, "Choose a pile to destroy.", pile1, pile2, game);

            if (choice) {
                destroyPermanents(pile1, game, source);
            } else {
                destroyPermanents(pile2, game, source);
            }

            return true;
        }
        return false;
    }

    private void destroyPermanents(List<Permanent> pile, Game game, Ability source) {
        for (Permanent permanent : pile) {
            if (permanent != null) {
                permanent.destroy(source.getSourceId(), game, true);
            }
        }
    }
}
