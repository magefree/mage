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
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SylvanOffering extends CardImpl {

    public SylvanOffering(UUID ownerId) {
        super(ownerId, 48, "Sylvan Offering", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{G}");
        this.expansionSetCode = "C14";


        // Choose an opponent. You and that player each put an X/X green Treefolk creature token onto the battlefield.
        this.getSpellAbility().addEffect(new SylvanOfferingEffect1());
        // Choose an opponent. You and that player each put X 1/1 green Elf Warrior creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new SylvanOfferingEffect2());
    }

    public SylvanOffering(final SylvanOffering card) {
        super(card);
    }

    @Override
    public SylvanOffering copy() {
        return new SylvanOffering(this);
    }
}

class SylvanOfferingEffect1 extends OneShotEffect {

    SylvanOfferingEffect1() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You and that player each put an X/X green Treefolk creature token onto the battlefield";
    }

    SylvanOfferingEffect1(final SylvanOfferingEffect1 effect) {
        super(effect);
    }

    @Override
    public SylvanOfferingEffect1 copy() {
        return new SylvanOfferingEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                int xValue = source.getManaCostsToPay().getX();
                Effect effect = new CreateTokenTargetEffect(new SylvanOfferingTreefolkToken(xValue));
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                effect.apply(game, source);
                new CreateTokenTargetEffect(new SylvanOfferingTreefolkToken(xValue)).apply(game, source);
                return true;
            }
        }
        return false;
    }
}

class SylvanOfferingTreefolkToken extends Token {

    public SylvanOfferingTreefolkToken(int xValue) {
        super("Treefolk", "X/X green Treefolk creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        subtype.add("Treefolk");
        color.setGreen(true);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

    }
}

class SylvanOfferingEffect2 extends OneShotEffect {

   SylvanOfferingEffect2() {
        super(Outcome.Sacrifice);
        this.staticText = "<br>Choose an opponent. You and that player each put X 1/1 green Elf Warrior creature tokens onto the battlefield";
    }

   SylvanOfferingEffect2(final SylvanOfferingEffect2 effect) {
        super(effect);
    }

    @Override
    public SylvanOfferingEffect2 copy() {
        return new SylvanOfferingEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                int xValue = source.getManaCostsToPay().getX();
                Effect effect = new CreateTokenTargetEffect(new SylvanOfferingElfWarriorToken(), xValue);
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                effect.apply(game, source);
                new CreateTokenEffect(new SylvanOfferingElfWarriorToken(), xValue).apply(game, source);
                return true;
            }
        }
        return false;
    }
}

class SylvanOfferingElfWarriorToken extends Token {

    public SylvanOfferingElfWarriorToken() {
        super("Elf Warrior", "1/1 green Elf Warrior creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        subtype.add("Elf");
        subtype.add("Warrior");
        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

    }
}
