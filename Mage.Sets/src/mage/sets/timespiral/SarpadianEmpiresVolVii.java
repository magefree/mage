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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.SaprolingToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public class SarpadianEmpiresVolVii extends CardImpl {

    public SarpadianEmpiresVolVii(UUID ownerId) {
        super(ownerId, 263, "Sarpadian Empires, Vol. VII", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "TSP";

        // As Sarpadian Empires, Vol. VII enters the battlefield, choose white Citizen, blue Camarid, black Thrull, red Goblin, or green Saproling.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseTokenEffect()));
        // {3}, {T}: Put a 1/1 creature token of the chosen color and type onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateSelectedTokenEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public SarpadianEmpiresVolVii(final SarpadianEmpiresVolVii card) {
        super(card);
    }

    @Override
    public SarpadianEmpiresVolVii copy() {
        return new SarpadianEmpiresVolVii(this);
    }
}

class ChooseTokenEffect extends OneShotEffect {

    public ChooseTokenEffect() {
        super(Outcome.Neutral);
        this.staticText = "choose white Citizen, blue Camarid, black Thrull, red Goblin, or green Saproling";
    }

    public ChooseTokenEffect(final ChooseTokenEffect effect) {
        super(effect);
    }

    @Override
    public ChooseTokenEffect copy() {
        return new ChooseTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if(sourceObject != null && controller != null) {
            ChoiceImpl choices = new ChoiceImpl(true);
            choices.setMessage("Choose token type");
            choices.getChoices().add("White Citizen");
            choices.getChoices().add("Blue Camarid");
            choices.getChoices().add("Black Thrull");
            choices.getChoices().add("Red Goblin");
            choices.getChoices().add("Green Saproling");
            if(controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(sourceObject.getLogName() + ": chosen token type is " + choices.getChoice());
                game.getState().setValue(source.getSourceId().toString() + "_SarpadianEmpiresVolVii", choices.getChoice());
                return true;
            }
        }
        return false;
    }
}

class CreateSelectedTokenEffect extends OneShotEffect {

    public CreateSelectedTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a 1/1 creature token of the chosen color and type onto the battlefield";
    }

    public CreateSelectedTokenEffect(final CreateSelectedTokenEffect effect) {
        super(effect);
    }

    @Override
    public CreateSelectedTokenEffect copy() {
        return new CreateSelectedTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String tokenType = game.getState().getValue(source.getSourceId().toString() + "_SarpadianEmpiresVolVii").toString();
        Token token;
        if(tokenType.equals("White Citizen")) {
            token = new CitizenToken();
        }
        else if(tokenType.equals("Blue Camarid")) {
            token = new CamaridToken();
        }
        else if(tokenType.equals("Black Thrull")) {
            token = new ThrullToken();
        }
        else if(tokenType.equals("Red Goblin")) {
            token = new GoblinToken();
        }
        else {
            token = new SaprolingToken();
        }
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }
}

class CitizenToken extends Token {
    public CitizenToken() {
        super("Citizen", "1/1 white Citizen creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Citizen");
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}

class CamaridToken extends Token {
    public CamaridToken() {
        super("Camarid", "1/1 blue Camarid creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Camarid");
        color.setBlue(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}

class ThrullToken extends Token {
    public ThrullToken() {
        super("Thrull", "1/1 black Thrull creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Thrull");
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
