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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PromiseOfPower extends CardImpl {

    public PromiseOfPower(UUID ownerId) {
        super(ownerId, 157, "Promise of Power", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}{B}{B}");
        this.expansionSetCode = "C14";


        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        
        // - You draw five cards and you lose 5 life.
        Effect effect =  new DrawCardSourceControllerEffect(5);
        effect.setText("You draw five cards");
        this.getSpellAbility().addEffect(effect);
        effect = new LoseLifeSourceControllerEffect(5);
        effect.setText("and you lose 5 life");
        this.getSpellAbility().addEffect(effect);

        // - Put an X/X black Demon creature token with flying onto the battlefield, where X is the number of cards in your hand.
        Mode mode = new Mode();
        mode.getEffects().add(new PromiseOfPowerEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {4}
        this.addAbility(new EntwineAbility("{4}"));
    }

    public PromiseOfPower(final PromiseOfPower card) {
        super(card);
    }

    @Override
    public PromiseOfPower copy() {
        return new PromiseOfPower(this);
    }
}

class PromiseOfPowerEffect extends OneShotEffect {

    public PromiseOfPowerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Put an X/X black Demon creature token with flying onto the battlefield, where X is the number of cards in your hand";
    }

    public PromiseOfPowerEffect(PromiseOfPowerEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return new CreateTokenEffect(new PromiseOfPowerDemonToken(controller.getHand().size())).apply(game, source);
        }
        return false;
    }

    @Override
    public PromiseOfPowerEffect copy() {
        return new PromiseOfPowerEffect(this);
    }

}
class PromiseOfPowerDemonToken extends Token {

    public PromiseOfPowerDemonToken(int xValue) {
        super("Demon", "X/X black Demon creature token with flying");
        setOriginalExpansionSetCode("C14");
        setTokenType(2);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Demon");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        addAbility(FlyingAbility.getInstance());
    }
}
