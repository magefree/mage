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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivationInfo;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author MarcoMarin
 */
public class FarrelitePriest extends CardImpl {

    public FarrelitePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}: Add {W} to your mana pool. If this ability has been activated four or more times this turn, sacrifice Farrelite Priest at the beginning of the next end step.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new ManaCostsImpl("{1}"));
        ability.addEffect(new FarrelitePriestEffect());
        this.addAbility(ability);
    }

    public FarrelitePriest(final FarrelitePriest card) {
        super(card);
    }

    @Override
    public FarrelitePriest copy() {
        return new FarrelitePriest(this);
    }
}

class FarrelitePriestEffect extends OneShotEffect {

    public FarrelitePriestEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public FarrelitePriestEffect(final FarrelitePriestEffect effect) {
        super(effect);
    }

    @Override
    public FarrelitePriestEffect copy() {
        return new FarrelitePriestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ActivationInfo activationInfo = ActivationInfo.getInstance(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        activationInfo.addActivation(game);
        if (activationInfo.getActivationCounter() == 4) {
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect());
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }

}
