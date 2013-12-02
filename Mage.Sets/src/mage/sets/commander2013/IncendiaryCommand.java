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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author LevelX2
 */
public class IncendiaryCommand extends CardImpl<IncendiaryCommand> {

    public IncendiaryCommand(UUID ownerId) {
        super(ownerId, 113, "Incendiary Command", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "C13";

        this.color.setRed(true);

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Incendiary Command deals 4 damage to target player;
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer(true));
        // or Incendiary Command deals 2 damage to each creature;
        Mode mode = new Mode();
        mode.getEffects().add(new DamageAllEffect(2, new FilterCreaturePermanent()));
        this.getSpellAbility().getModes().addMode(mode);
        // or destroy target nonbasic land;
        mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetNonBasicLandPermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // or each player discards all the cards in his or her hand, then draws that many cards.
        mode = new Mode();
        mode.getEffects().add(new IncendiaryCommandDrawEffect());
        this.getSpellAbility().getModes().addMode(mode);

    }

    public IncendiaryCommand(final IncendiaryCommand card) {
        super(card);
    }

    @Override
    public IncendiaryCommand copy() {
        return new IncendiaryCommand(this);
    }
}

class IncendiaryCommandDrawEffect extends OneShotEffect<IncendiaryCommandDrawEffect> {

    public IncendiaryCommandDrawEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player discards all the cards in his or her hand, then draws that many cards";
    }

    public IncendiaryCommandDrawEffect(final IncendiaryCommandDrawEffect effect) {
        super(effect);
    }

    @Override
    public IncendiaryCommandDrawEffect copy() {
        return new IncendiaryCommandDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cards = player.getHand().size();
                    if (cards > 0) {
                        player.discard(cards, source, game);
                        player.drawCards(cards, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
