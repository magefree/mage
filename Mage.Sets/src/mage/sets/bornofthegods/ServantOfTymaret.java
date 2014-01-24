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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class ServantOfTymaret extends CardImpl<ServantOfTymaret> {

    public ServantOfTymaret(UUID ownerId) {
        super(ownerId, 82, "Servant of Tymaret", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // <i>Inspired</i> - Whenever Servant of Tymaret becomes untapped, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new InspiredAbility(new ServantOfTymaretEffect()));

        // {2}{B}: Regenerate Servant of Tymaret.
    }

    public ServantOfTymaret(final ServantOfTymaret card) {
        super(card);
    }

    @Override
    public ServantOfTymaret copy() {
        return new ServantOfTymaret(this);
    }
}

class ServantOfTymaretEffect extends OneShotEffect<ServantOfTymaretEffect> {

    public ServantOfTymaretEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 1 life. You gain life equal to the life lost this way";
    }

    public ServantOfTymaretEffect(final ServantOfTymaretEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            damage += game.getPlayer(opponentId).damage(1, source.getSourceId(), game, false, true);
        }
        game.getPlayer(source.getControllerId()).gainLife(damage, game);
        return true;
    }

    @Override
    public ServantOfTymaretEffect copy() {
        return new ServantOfTymaretEffect(this);
    }

}
