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
package mage.sets.conspiracy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class MagusOfTheMirror extends CardImpl {

    public MagusOfTheMirror(UUID ownerId) {
        super(ownerId, 117, "Magus of the Mirror", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "CNS";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {tap}, Sacrifice Magus of the Mirror: Exchange life totals with target opponent. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, 
                new MagusOfTheMirrorEffect(), 
                new TapSourceCost(),
                new IsStepCondition(PhaseStep.UPKEEP),
                null);
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public MagusOfTheMirror(final MagusOfTheMirror card) {
        super(card);
    }

    @Override
    public MagusOfTheMirror copy() {
        return new MagusOfTheMirror(this);
    }
}

class MagusOfTheMirrorEffect extends OneShotEffect {

    public MagusOfTheMirrorEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exchange life totals with target opponent";
    }

    public MagusOfTheMirrorEffect(final MagusOfTheMirrorEffect effect) {
        super(effect);
    }

    @Override
    public MagusOfTheMirrorEffect copy() {
        return new MagusOfTheMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            int lifeController = controller.getLife();
            int lifeOpponent = opponent.getLife();

            if (lifeController == lifeOpponent)
                return false;

            if (!controller.isLifeTotalCanChange() || !opponent.isLifeTotalCanChange())
                return false;

            // 20110930 - 118.7, 118.8
            if (lifeController < lifeOpponent && (!controller.isCanGainLife() || !opponent.isCanLoseLife()))
                return false;

            if (lifeController > lifeOpponent && (!controller.isCanLoseLife() || !opponent.isCanGainLife()))
                return false;

            controller.setLife(lifeOpponent, game);
            opponent.setLife(lifeController, game);
            return true;
        }
        return false;
    }
}