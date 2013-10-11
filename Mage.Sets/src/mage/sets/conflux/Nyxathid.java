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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class Nyxathid extends CardImpl<Nyxathid> {

    public Nyxathid(UUID ownerId) {
        super(ownerId, 49, "Nyxathid", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "CON";
        this.subtype.add("Elemental");

        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // As Nyxathid enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponent()));

        // Nyxathid gets -1/-1 for each card in the chosen player's hand.
        DynamicValue chosenPlayerHand = new SignInversionDynamicValue(new CardsInChosenPlayerHandCount());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(chosenPlayerHand, chosenPlayerHand, Duration.WhileOnBattlefield)));

    }

    public Nyxathid(final Nyxathid card) {
        super(card);
    }

    @Override
    public Nyxathid copy() {
        return new Nyxathid(this);
    }
}

class ChooseOpponent extends OneShotEffect<ChooseOpponent> {

    public ChooseOpponent() {
        super(Outcome.Neutral);
        this.staticText = "choose an opponent";
    }

    public ChooseOpponent(final ChooseOpponent effect) {
        super(effect);
    }

    @Override
    public ChooseOpponent copy() {
        return new ChooseOpponent(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            TargetOpponent target = new TargetOpponent();
            target.setRequired(true);
            target.setNotTarget(true);
            if (player.choose(this.outcome, target, source.getSourceId(), game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + chosenPlayer.getName());
                    game.getState().setValue(permanent.getId() + "_player", target.getFirstTarget());
                    return true;
                }
            }
        }
        return false;
    }
}

class CardsInChosenPlayerHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + "_player");
            Player chosenPlayer = game.getPlayer(playerId);
            if (chosenPlayer != null) {
                return chosenPlayer.getHand().size();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInChosenPlayerHandCount();
    }

    @Override
    public String getMessage() {
        return "cards in chosen opponents hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}