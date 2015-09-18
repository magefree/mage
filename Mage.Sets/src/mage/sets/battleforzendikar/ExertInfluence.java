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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
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
public class ExertInfluence extends CardImpl {

    public ExertInfluence(UUID ownerId) {
        super(ownerId, 77, "Exert Influence", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}");
        this.expansionSetCode = "BFZ";

        // <i>Converge</i>-Gain control of target creature if its power is less than or equal to the number of colors spent to cast Exert Influence.
        getSpellAbility().addEffect(new ExertInfluenceEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public ExertInfluence(final ExertInfluence card) {
        super(card);
    }

    @Override
    public ExertInfluence copy() {
        return new ExertInfluence(this);
    }
}

class ExertInfluenceEffect extends OneShotEffect {

    public ExertInfluenceEffect() {
        super(Outcome.GainControl);
        this.staticText = "<i>Converge</i>-Gain control of target creature if its power is less than or equal to the number of colors spent to cast {this}";
    }

    public ExertInfluenceEffect(final ExertInfluenceEffect effect) {
        super(effect);
    }

    @Override
    public ExertInfluenceEffect copy() {
        return new ExertInfluenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && sourceObject != null) {
            int colors = new ColorsOfManaSpentToCastCount().calculate(game, source, this);
            if (targetCreature.getPower().getValue() <= colors) {
                game.addEffect(new GainControlTargetEffect(Duration.Custom, true), source);
            }
            return true;
        }
        return false;
    }
}
