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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class ImaginaryThreats extends CardImpl {

    public ImaginaryThreats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Creatures target opponent controls attack this turn if able. During that player's next untap step, creatures he or she controls don't untap.
        getSpellAbility().addEffect(new ImaginaryThreatsEffect());
        getSpellAbility().addWatcher(new AttackedThisTurnWatcher());
        getSpellAbility().addTarget(new TargetOpponent());
        getSpellAbility().addEffect(new DontUntapInPlayersNextUntapStepAllEffect(new FilterCreaturePermanent())
                .setText("During that player's next untap step, creatures he or she controls don't untap"));
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));

    }

    public ImaginaryThreats(final ImaginaryThreats card) {
        super(card);
    }

    @Override
    public ImaginaryThreats copy() {
        return new ImaginaryThreats(this);
    }
}

class ImaginaryThreatsEffect extends OneShotEffect {

    public ImaginaryThreatsEffect() {
        super(Outcome.Detriment);
        staticText = "Creatures target opponent controls attack this turn if able";
    }

    public ImaginaryThreatsEffect(final ImaginaryThreatsEffect effect) {
        super(effect);
    }

    @Override
    public ImaginaryThreatsEffect copy() {
        return new ImaginaryThreatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            RequirementEffect effect = new AttacksIfAbleAllEffect(filter, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
