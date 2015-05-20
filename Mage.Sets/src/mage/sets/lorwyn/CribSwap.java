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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CribSwap extends CardImpl {

    public CribSwap(UUID ownerId) {
        super(ownerId, 11, "Crib Swap", Rarity.UNCOMMON, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Shapeshifter");


        // Changeling
        this.addAbility(ChangelingAbility.getInstance());
        // Exile target creature. Its controller puts a 1/1 colorless Shapeshifter creature token with changeling onto the battlefield.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CribSwapEffect());

    }

    public CribSwap(final CribSwap card) {
        super(card);
    }

    @Override
    public CribSwap copy() {
        return new CribSwap(this);
    }
}

class CribSwapEffect extends OneShotEffect {

    public CribSwapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Its controller puts a 1/1 colorless Shapeshifter creature token with changeling onto the battlefield";
    }

    public CribSwapEffect(final CribSwapEffect effect) {
        super(effect);
    }

    @Override
    public CribSwapEffect copy() {
        return new CribSwapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                CribSwapShapeshifterWhiteToken token = new CribSwapShapeshifterWhiteToken();
                return token.putOntoBattlefield(1, game, source.getSourceId(), targetCreature.getControllerId());
            }
        }
        return false;
    }
}

class CribSwapShapeshifterWhiteToken extends Token {

    public CribSwapShapeshifterWhiteToken() {
        super("Shapeshifter", "1/1 colorless Shapeshifter creature token with changeling");
        this.setOriginalExpansionSetCode("LRW");
        cardType.add(CardType.CREATURE);
        subtype.add("Shapeshifter");
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(ChangelingAbility.getInstance());
    }
}
