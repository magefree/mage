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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import java.util.UUID;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Alvin
 */
public class AbattoirGhoul extends CardImpl {

    public AbattoirGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add("Zombie");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever a creature dealt damage by Abattoir Ghoul this turn dies, you gain life equal to that creature's toughness.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new AbattoirGhoulEffect(), false));
    }

    public AbattoirGhoul(final AbattoirGhoul card) {
        super(card);
    }

    @Override
    public AbattoirGhoul copy() {
        return new AbattoirGhoul(this);
    }
}

class AbattoirGhoulEffect extends OneShotEffect {

    public AbattoirGhoulEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to that creature's toughness";
    }

    public AbattoirGhoulEffect(final AbattoirGhoulEffect effect) {
        super(effect);
    }

    @Override
    public AbattoirGhoulEffect copy() {
        return new AbattoirGhoulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            Permanent creature = (Permanent) game.getLastKnownInformation(card.getId(), Zone.BATTLEFIELD);
            if (creature != null) {
                int toughness = creature.getToughness().getValue();
                if (you != null) {
                    you.gainLife(toughness, game);
                    return true;
                }
            }
        }
        return false;
    }
}