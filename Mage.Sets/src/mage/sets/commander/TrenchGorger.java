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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class TrenchGorger extends CardImpl {

    public TrenchGorger(UUID ownerId) {
        super(ownerId, 65, "Trench Gorger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Leviathan");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Trench Gorger enters the battlefield, you may search your library for any number of land cards, exile them, then shuffle your library. If you do, Trench Gorger's power and toughness each become equal to the number of cards exiled this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TrenchGorgerEffect(), true));

    }

    public TrenchGorger(final TrenchGorger card) {
        super(card);
    }

    @Override
    public TrenchGorger copy() {
        return new TrenchGorger(this);
    }
}

class TrenchGorgerEffect extends OneShotEffect {

    public TrenchGorgerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "you may search your library for any number of land cards, exile them, then shuffle your library. If you do, {this}'s power and toughness each become equal to the number of cards exiled this way";
    }

    public TrenchGorgerEffect(final TrenchGorgerEffect effect) {
        super(effect);
    }

    @Override
    public TrenchGorgerEffect copy() {
        return new TrenchGorgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, new FilterLandCard("any number of land cards"));
            target.choose(outcome, controller.getId(), controller.getId(), game);
            int count = 0;
            for (UUID cardId: target.getTargets()) {
                Card card = game.getCard(cardId);
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                count++;
            }
            controller.shuffleLibrary(game);
            game.addEffect(new SetPowerToughnessSourceEffect(count, count, Duration.EndOfGame), source);
            return true;
        }
        return false;
    }
}
