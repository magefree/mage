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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ChainersTormentNightmareToken;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class ChainersTorment extends CardImpl {

    public ChainersTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I, II — Chainer's Torment deals 2 damage to each opponent and you gain 2 life.
        Ability ability = sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new DamagePlayersEffect(2, TargetController.OPPONENT));
        ability.addEffect(new GainLifeEffect(2).setText("and you gain 2 life"));

        // III — Create an X/X black Nightmare Horror creature token, where X is half your life total, rounded up. It deals X damage to you.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ChainersTormentEffect());
        this.addAbility(sagaAbility);
    }

    public ChainersTorment(final ChainersTorment card) {
        super(card);
    }

    @Override
    public ChainersTorment copy() {
        return new ChainersTorment(this);
    }
}

class ChainersTormentEffect extends OneShotEffect {

    ChainersTormentEffect() {
        super(Outcome.Benefit);
        this.staticText = "Create an X/X black Nightmare Horror creature token, where X is half your life total, rounded up. It deals X damage to you";
    }

    ChainersTormentEffect(final ChainersTormentEffect effect) {
        super(effect);
    }

    @Override
    public ChainersTormentEffect copy() {
        return new ChainersTormentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = (int) Math.ceil((1.0 * Math.max(0, player.getLife())) / 2);
        CreateTokenEffect effect = new CreateTokenEffect(new ChainersTormentNightmareToken(xValue));
        if (effect.apply(game, source)) {
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                Permanent token = game.getPermanentOrLKIBattlefield(tokenId);
                if (token != null) {
                    player.damage(xValue, tokenId, game, false, true);
                }
            }
        }
        return true;
    }
}
