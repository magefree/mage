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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class ArgentSphinx extends CardImpl {

    public ArgentSphinx(UUID ownerId) {
        super(ownerId, 28, "Argent Sphinx", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Sphinx");

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Metalcraft - {U}: Exile Argent Sphinx. Return it to the battlefield under your control at the beginning of the next end step. Activate this ability only if you control three or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new ArgentSphinxEffect(), new ManaCostsImpl("{U}"), MetalcraftCondition.getInstance());
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        this.addAbility(ability);
    }

    public ArgentSphinx(final ArgentSphinx card) {
        super(card);
    }

    @Override
    public ArgentSphinx copy() {
        return new ArgentSphinx(this);
    }

}

class ArgentSphinxEffect extends OneShotEffect {

    private static final String effectText = "Exile {this}. Return it to the battlefield under your control at the beginning of the next end step";

    ArgentSphinxEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    ArgentSphinxEffect(ArgentSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game)) {
                //create delayed triggered ability
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility
                        = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                                new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD));
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                delayedAbility.setSourceObject(source.getSourceObject(game), game);
                game.addDelayedTriggeredAbility(delayedAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArgentSphinxEffect copy() {
        return new ArgentSphinxEffect(this);
    }

}
