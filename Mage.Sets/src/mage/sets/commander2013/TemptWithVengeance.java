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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
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
public class TemptWithVengeance extends CardImpl<TemptWithVengeance> {

    public TemptWithVengeance(UUID ownerId) {
        super(ownerId, 125, "Tempt with Vengeance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "C13";

        this.color.setRed(true);

        // Tempting offer - Put X 1/1 red Elemental creature tokens with haste onto the battlefield. Each opponent may put X 1/1 red Elemental creature tokens with haste onto the battlefield. For each player who does, put X 1/1 red Elemental creature tokens with haste onto the battlefield.
       this.getSpellAbility().addEffect(new TemptWithVengeanceEffect());
    }

    public TemptWithVengeance(final TemptWithVengeance card) {
        super(card);
    }

    @Override
    public TemptWithVengeance copy() {
        return new TemptWithVengeance(this);
    }
}

class TemptWithVengeanceEffect extends OneShotEffect<TemptWithVengeanceEffect> {

    public TemptWithVengeanceEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "<i>Tempting offer</i> - Put X 1/1 red Elemental creature tokens with haste onto the battlefield. Each opponent may put X 1/1 red Elemental creature tokens with haste onto the battlefield. For each player who does, put X 1/1 red Elemental creature tokens with haste onto the battlefield";
    }

    public TemptWithVengeanceEffect(final TemptWithVengeanceEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithVengeanceEffect copy() {
        return new TemptWithVengeanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (controller != null && xValue > 0) {

            Token tokenCopy = new TemptWithVengeanceElementalToken();
            tokenCopy.putOntoBattlefield(xValue, game, source.getSourceId(), source.getControllerId(), false, false);

            int opponentsAddedTokens = 0;
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, new StringBuilder("Put ").append(xValue).append(" Elemental Tokens onto the battlefield?").toString(), game)) {
                        opponentsAddedTokens += xValue;
                        tokenCopy.putOntoBattlefield(xValue, game, source.getSourceId(), playerId, false, false);
                    }
                }
            }
            if (opponentsAddedTokens > 0) {
                tokenCopy.putOntoBattlefield(opponentsAddedTokens, game, source.getSourceId(), source.getControllerId(), false, false);
            }
            return true;
        }

        return false;
    }
}

class TemptWithVengeanceElementalToken extends Token {

    public TemptWithVengeanceElementalToken() {
        super("Elemental", "1/1 red Elemental creature tokens with haste");
        cardType.add(CardType.CREATURE);
        subtype.add("Elemental");

        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }
}
