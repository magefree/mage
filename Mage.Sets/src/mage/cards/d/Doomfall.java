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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class Doomfall extends CardImpl {

    public Doomfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // • Target opponent exiles a creature he or she controls.
        this.getSpellAbility().addEffect(new DoomfallEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Target opponent reveals his or her hand. You choose a nonland card from it. Exile that card.
        Mode mode = new Mode();
        mode.getEffects().add(new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_A_NON_LAND)
                .setText("Target opponent reveals his or her hand. You choose a nonland card from it. Exile that card"));
        mode.getTargets().add(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    public Doomfall(final Doomfall card) {
        super(card);
    }

    @Override
    public Doomfall copy() {
        return new Doomfall(this);
    }
}

class DoomfallEffect extends OneShotEffect {

    public DoomfallEffect() {
        super(Outcome.Exile);
        this.staticText = "target player exiles a creature he or she controls";
    }

    public DoomfallEffect(final DoomfallEffect effect) {
        super(effect);
    }

    @Override
    public DoomfallEffect copy() {
        return new DoomfallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            Target target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            if (targetPlayer.choose(outcome, target, source.getSourceId(), game)) {
                targetPlayer.moveCards(game.getPermanent(target.getFirstTarget()), Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }
}
