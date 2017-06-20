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
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author andyfries
 */
public class MaralenOfTheMornsong extends CardImpl {

    public MaralenOfTheMornsong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Elf");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Players can't draw cards.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaralenOfTheMornsongEffect()));

        // At the beginning of each player's draw step, that player loses 3 life, searches his or her library for a card, puts it into his or her hand, then shuffles his or her library.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new MaralenOfTheMornsongEffect2(), TargetController.ANY, false));

    }

    public MaralenOfTheMornsong(final MaralenOfTheMornsong card) {
        super(card);
    }

    @Override
    public MaralenOfTheMornsong copy() {
        return new MaralenOfTheMornsong(this);
    }
}

class MaralenOfTheMornsongEffect extends ContinuousRuleModifyingEffectImpl {

    public MaralenOfTheMornsongEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players can't draw cards";
    }

    public MaralenOfTheMornsongEffect(final MaralenOfTheMornsongEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MaralenOfTheMornsongEffect copy() {
        return new MaralenOfTheMornsongEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}

class MaralenOfTheMornsongEffect2 extends OneShotEffect {

    public MaralenOfTheMornsongEffect2() {
        super(Outcome.LoseLife);
        staticText = "that player loses 3 life, searches his or her library for a card, puts it into his or her hand, then shuffles his or her library";
    }

    public MaralenOfTheMornsongEffect2(final MaralenOfTheMornsongEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID activePlayerId = game.getActivePlayerId();
        Player player = game.getPlayer(activePlayerId);
        if (player != null) {
            player.loseLife(3, game, false);
            if (player != null) {
                TargetCardInLibrary target = new TargetCardInLibrary();
                if (player.searchLibrary(target, game)) {
                    player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
                }
                player.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public MaralenOfTheMornsongEffect2 copy() {
        return new MaralenOfTheMornsongEffect2(this);
    }

}
