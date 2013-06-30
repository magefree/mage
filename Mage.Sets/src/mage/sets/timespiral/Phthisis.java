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
package mage.sets.timespiral;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Phthisis extends CardImpl<Phthisis> {

    public Phthisis(UUID ownerId) {
        super(ownerId, 122, "Phthisis", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{B}{B}{B}{B}");
        this.expansionSetCode = "TSP";

        this.color.setBlack(true);

        // Destroy target creature. Its controller loses life equal to its power plus its toughness.
        this.getSpellAbility().addEffect(new PhthisisEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));

        // Suspend 5-{1}{B}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl("{1}{B}"), this));
    }

    public Phthisis(final Phthisis card) {
        super(card);
    }

    @Override
    public Phthisis copy() {
        return new Phthisis(this);
    }
}

class PhthisisEffect extends OneShotEffect<PhthisisEffect> {

    public PhthisisEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Its controller loses life equal to its power plus its toughness";
    }

    public PhthisisEffect(final PhthisisEffect effect) {
        super(effect);
    }

    @Override
    public PhthisisEffect copy() {
        return new PhthisisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                int lifeLoss = creature.getPower().getValue() + creature.getToughness().getValue();
                creature.destroy(source.getSourceId(), game, false);
                // the life loss happens also if the creature is indestructible or regenerated (legal targets)
                controller.loseLife(lifeLoss, game);
                return true;
            }
        }
        return false;
    }
}
