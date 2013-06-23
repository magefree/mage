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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class OonaQueenOfTheFae extends CardImpl<OonaQueenOfTheFae> {

    public OonaQueenOfTheFae(UUID ownerId) {
        super(ownerId, 172, "Oona, Queen of the Fae", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U/B}{U/B}{U/B}");
        this.expansionSetCode = "SHM";
        this.supertype.add("Legendary");
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {X}{UB}: Choose a color. Target opponent exiles the top X cards of his or her library. For each card of the chosen color exiled this way, put a 1/1 blue and black Faerie Rogue creature token with flying onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OonaQueenOfTheFaeEffect(), new ManaCostsImpl("{X}{U/B}"));
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
    }

    public OonaQueenOfTheFae(final OonaQueenOfTheFae card) {
        super(card);
    }

    @Override
    public OonaQueenOfTheFae copy() {
        return new OonaQueenOfTheFae(this);
    }
}

class OonaQueenOfTheFaeEffect extends OneShotEffect<OonaQueenOfTheFaeEffect> {

    public OonaQueenOfTheFaeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose a color. Target opponent exiles the top X cards of his or her library. For each card of the chosen color exiled this way, put a 1/1 blue and black Faerie Rogue creature token with flying onto the battlefield";
    }

    public OonaQueenOfTheFaeEffect(final OonaQueenOfTheFaeEffect effect) {
        super(effect);
    }

    @Override
    public OonaQueenOfTheFaeEffect copy() {
        return new OonaQueenOfTheFaeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor();
        controller.choose(outcome, choice, game);
        if (choice.getColor() != null) {
            int cardsWithColor = 0;
            int cardsToExile = Math.min(opponent.getLibrary().size(), source.getManaCostsToPay().getX());
            for(int i = 0; i < cardsToExile; i++) {
                Card card = opponent.getLibrary().removeFromTop(game);
                if (card != null) {
                    if (card.getColor().contains(choice.getColor())) {
                        cardsWithColor++;
                    }
                    card.moveToExile(null, null, source.getSourceId(), game);
                }
            }
            if (cardsWithColor > 0) {
                new CreateTokenEffect(new OonaQueenFaerieToken(), cardsWithColor).apply(game, source);
            }
            game.informPlayers(new StringBuilder("Oona: ").append(cardsWithColor).append(" Token").append(cardsWithColor != 1?"s":"").append(" created").toString());
            return true;
        }
        return false;
    }
}

class OonaQueenFaerieToken extends Token {
    OonaQueenFaerieToken() {
        super("Faerie Rogue", "1/1 blue and black Faerie Rogue creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add("Faerie");
        subtype.add("Rogue");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}
