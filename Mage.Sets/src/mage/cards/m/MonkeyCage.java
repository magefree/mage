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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LoneFox
 */
public class MonkeyCage extends CardImpl {

    public MonkeyCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // When a creature enters the battlefield, sacrifice Monkey Cage and create X 2/2 green Ape creature tokens, where X is that creature's converted mana cost.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(),
            new FilterCreaturePermanent("a creature"), false, SetTargetPointer.PERMANENT, "");
        ability.addEffect(new MonkeyCageEffect());
        this.addAbility(ability);
    }

    public MonkeyCage(final MonkeyCage card) {
        super(card);
    }

    @Override
    public MonkeyCage copy() {
        return new MonkeyCage(this);
    }
}

class MonkeyCageEffect extends OneShotEffect {

    public MonkeyCageEffect() {
        super(Outcome.Benefit);
        staticText = "and create X 2/2 green Ape creature tokens, where X is that creature's converted mana cost";
    }

    public MonkeyCageEffect(final MonkeyCageEffect effect) {
        super(effect);
    }

    @Override
    public MonkeyCageEffect copy() {
        return new MonkeyCageEffect(this);
    }

    public boolean apply(Game game, Ability source) {
         Permanent creature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
         if(creature != null) {
             int cmc = creature.getConvertedManaCost();
             return new CreateTokenEffect(new ApeToken(), cmc).apply(game, source);
         }
         return false;
    }
}

class ApeToken extends Token {
    ApeToken() {
        super("Ape", "2/2 green Ape creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Ape");
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}
