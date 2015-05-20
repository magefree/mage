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
package mage.sets.commander;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.PreventAllDamageByAllEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class PollenLullaby extends CardImpl {

    public PollenLullaby(UUID ownerId) {
        super(ownerId, 26, "Pollen Lullaby", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "CMD";


        // Prevent all combat damage that would be dealt this turn. Clash with an opponent. If you win, creatures that player controls don't untap during the player's next untap step.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(new PollenLullabyEffect(), true, null));
    }

    public PollenLullaby(final PollenLullaby card) {
        super(card);
    }

    @Override
    public PollenLullaby copy() {
        return new PollenLullaby(this);
    }
}

class PollenLullabyEffect extends OneShotEffect {

    public PollenLullabyEffect() {
        super(Outcome.Tap);
        staticText = "creatures that player controls don't untap during the player's next untap step";
    }

    public PollenLullabyEffect(final PollenLullabyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Permanent creature: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                creature.tap(game);
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
                effect.setTargetPointer(new FixedTarget(creature.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public PollenLullabyEffect copy() {
        return new PollenLullabyEffect(this);
    }

}

