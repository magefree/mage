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
package mage.cards.m;

import java.util.UUID;

import mage.constants.CardType;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.abilities.Ability;
import mage.game.permanent.Permanent;
import mage.constants.Outcome;
import mage.game.permanent.token.Token;
import mage.MageInt;

/**
 * @author duncant
 */
public class MercyKilling extends CardImpl {

    public MercyKilling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G/W}");
        
        // Target creature's controller sacrifices it, then puts X 1/1 green and white Elf Warrior creature tokens onto the battlefield, where X is that creature's power.
        this.getSpellAbility().addEffect(new SacrificeTargetEffect("Target creature's controller sacrifices it"));
        this.getSpellAbility().addEffect(new MercyKillingTokenEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public MercyKilling(final MercyKilling card) {
        super(card);
    }

    @Override
    public MercyKilling copy() {
        return new MercyKilling(this);
    }
}

class MercyKillingTokenEffect extends OneShotEffect {

    public MercyKillingTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = ", then puts X 1/1 green and white Elf Warrior creature tokens onto the battlefield, where X is that creature's power";
    }

    public MercyKillingTokenEffect(final MercyKillingTokenEffect effect) {
        super(effect);
    }

    @Override
    public MercyKillingTokenEffect copy() {
        return new MercyKillingTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            int power = permanent.getPower().getValue();
            return new MercyKillingToken().putOntoBattlefield(power, game, source.getSourceId(), permanent.getControllerId());
        }
        return false;
    }

}

class MercyKillingToken extends Token {

    public MercyKillingToken() {
        super("Elf Warrior", "1/1 green and white Elf Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add("Elf");
        subtype.add("Warrior");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
