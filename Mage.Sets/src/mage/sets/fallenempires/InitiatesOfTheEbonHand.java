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
package mage.sets.fallenempires;

import java.util.UUID;
import mage.MageInt;
import static mage.Mana.BlackMana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public class InitiatesOfTheEbonHand extends CardImpl {

    public InitiatesOfTheEbonHand(UUID ownerId) {
        super(ownerId, 16, "Initiates of the Ebon Hand", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "FEM";
        this.subtype.add("Cleric");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Add {B} to your mana pool. If this ability has been activated four or more times this turn, sacrifice Initiates of the Ebon Hand at the beginning of the next end step.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BasicManaEffect(BlackMana(1)),
                new ManaCostsImpl("{1}"));
        ability.addEffect(new InitiatesOfTheEbonHandEffect());
        this.addAbility(ability);  
    }

    public InitiatesOfTheEbonHand(final InitiatesOfTheEbonHand card) {
        super(card);
    }

    @Override
    public InitiatesOfTheEbonHand copy() {
        return new InitiatesOfTheEbonHand(this);
    }
}

class InitiatesOfTheEbonHandEffect extends OneShotEffect {

    public InitiatesOfTheEbonHandEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public InitiatesOfTheEbonHandEffect(final InitiatesOfTheEbonHandEffect effect) {
        super(effect);
    }

    @Override
    public InitiatesOfTheEbonHandEffect copy() {
        return new InitiatesOfTheEbonHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amount = (Integer) game.getState().getValue(source.getSourceId().toString() + "InitiatesOfTheEbonHand");
        if (amount == null) {
            amount = 0;
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new InitiatesOfTheEbonHandResetEffect());
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        amount++;
        game.getState().setValue(source.getSourceId().toString() + "InitiatesOfTheEbonHand", amount);

        return true;
    }
}

class InitiatesOfTheEbonHandResetEffect extends OneShotEffect {

    public InitiatesOfTheEbonHandResetEffect() {
        super(Outcome.Neutral);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public InitiatesOfTheEbonHandResetEffect(final InitiatesOfTheEbonHandResetEffect effect) {
        super(effect);
    }

    @Override
    public InitiatesOfTheEbonHandResetEffect copy() {
        return new InitiatesOfTheEbonHandResetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {  
        Integer amount = (Integer) game.getState().getValue(source.getSourceId().toString() + "InitiatesOfTheEbonHand");
        if (amount != null && amount >= 4) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        game.getState().setValue(source.getSourceId().toString() + "InitiatesOfTheEbonHand", null);

        return true;
    }
}