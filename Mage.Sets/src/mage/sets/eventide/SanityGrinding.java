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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class SanityGrinding extends CardImpl<SanityGrinding> {

    public SanityGrinding(UUID ownerId) {
        super(ownerId, 29, "Sanity Grinding", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{U}{U}{U}");
        this.expansionSetCode = "EVE";

        this.color.setBlue(true);

        // Chroma - Reveal the top ten cards of your library. For each blue mana symbol in the mana costs of the revealed cards, target opponent puts the top card of his or her library into his or her graveyard. Then put the cards you revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new SanityGrindingEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));

    }

    public SanityGrinding(final SanityGrinding card) {
        super(card);
    }

    @Override
    public SanityGrinding copy() {
        return new SanityGrinding(this);
    }
}

class SanityGrindingEffect extends OneShotEffect<SanityGrindingEffect> {

    public SanityGrindingEffect() {
        super(Outcome.Neutral);
        staticText = "<i>Chroma</i> - Reveal the top ten cards of your library. For each blue mana symbol in the mana costs of the revealed cards, target opponent puts the top card of his or her library into his or her graveyard. Then put the cards you revealed this way on the bottom of your library in any order";
    }

    public SanityGrindingEffect(final SanityGrindingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        Cards revealed = new CardsImpl();
        int amount;
        if (you == null) {
            return false;
        }
        amount = (Math.min(10, you.getLibrary().size()));
        for (int i = 0; i < amount; i++) {
            revealed.add(you.getLibrary().removeFromTop(game));
        }
        you.revealCards("Sanity Grinding", revealed, game);
        if (targetOpponent != null) {
            amount = (Math.min(targetOpponent.getLibrary().size(), new ChromaSanityGrindingCount(revealed).calculate(game, source)));
            for (int i = 0; i < amount; i++) {
                targetOpponent.getLibrary().removeFromTop(game).moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false);
            }
        }
        return you.putCardsOnBottomOfLibrary(revealed, game, source, true);
    }

    @Override
    public SanityGrindingEffect copy() {
        return new SanityGrindingEffect(this);
    }
}

class ChromaSanityGrindingCount implements DynamicValue {

    private Cards revealed;

    public ChromaSanityGrindingCount(Cards revealed) {
        this.revealed = revealed;
    }

    public ChromaSanityGrindingCount(final ChromaSanityGrindingCount dynamicValue) {
        this.revealed = dynamicValue.revealed;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int chroma = 0;
        for (Card card : revealed.getCards(game)) {
            chroma += card.getManaCost().getMana().getBlue();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaSanityGrindingCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
