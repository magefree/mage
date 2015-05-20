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
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BenevolentOffering extends CardImpl {

    public BenevolentOffering(UUID ownerId) {
        super(ownerId, 3, "Benevolent Offering", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "C14";


        // Choose an opponent. You and that player each put three 1/1 white Spirit creature tokens with flying onto the battlefield.
        this.getSpellAbility().addEffect(new BenevolentOfferingEffect1());

        // Choose an opponent. You gain 2 life for each creature you control and that player gains 2 life for each creature he or she controls.
        this.getSpellAbility().addEffect(new BenevolentOfferingEffect2());
    }

    public BenevolentOffering(final BenevolentOffering card) {
        super(card);
    }

    @Override
    public BenevolentOffering copy() {
        return new BenevolentOffering(this);
    }
}

class BenevolentOfferingEffect1 extends OneShotEffect {

    BenevolentOfferingEffect1() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You and that player each put three 1/1 white Spirit creature tokens with flying onto the battlefield";
    }

    BenevolentOfferingEffect1(final BenevolentOfferingEffect1 effect) {
        super(effect);
    }

    @Override
    public BenevolentOfferingEffect1 copy() {
        return new BenevolentOfferingEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                Effect effect = new CreateTokenTargetEffect(new SpiritWhiteToken("C14"), 3);
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                effect.apply(game, source);
                new CreateTokenEffect(new SpiritWhiteToken("C14"), 3).apply(game, source);
                return true;
            }
        }
        return false;
    }
}

class BenevolentOfferingEffect2 extends OneShotEffect {

    BenevolentOfferingEffect2() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You gain 2 life for each creature you control and that player gains 2 life for each creature he or she controls";
    }

    BenevolentOfferingEffect2(final BenevolentOfferingEffect2 effect) {
        super(effect);
    }

    @Override
    public BenevolentOfferingEffect2 copy() {
        return new BenevolentOfferingEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                int count = game.getBattlefield().countAll(new FilterCreaturePermanent(), controller.getId(), game) * 2;
                controller.gainLife(count, game);
                count = game.getBattlefield().countAll(new FilterCreaturePermanent(), opponent.getId(), game) * 2;
                opponent.gainLife(count, game);
                return true;
            }
        }
        return false;
    }
}
