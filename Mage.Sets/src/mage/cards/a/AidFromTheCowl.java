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
package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class AidFromTheCowl extends CardImpl {

    private static final String ruleText = "<i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, "
            + "you may reveal the top card of your library. If it's a permanent card, you may put it onto the battlefield. Otherwise, put it on the bottom of your library.";

    public AidFromTheCowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn,
        // reveal the top card of your library. If it is a permanent card, you may put it onto the battlefield. Otherwise, put it on the bottom of your library.
        TriggeredAbility ability = new BeginningOfYourEndStepTriggeredAbility(new AidFromTheCowlEffect(), false);
        this.addAbility(new ConditionalTriggeredAbility(ability, RevoltCondition.instance, ruleText), new RevoltWatcher());
    }

    public AidFromTheCowl(final AidFromTheCowl card) {
        super(card);
    }

    @Override
    public AidFromTheCowl copy() {
        return new AidFromTheCowl(this);
    }
}

class AidFromTheCowlEffect extends OneShotEffect {

    public AidFromTheCowlEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a permanent card, you may put it onto the battlefield. Otherwise, you may put that card on the bottom of your library";
    }

    public AidFromTheCowlEffect(final AidFromTheCowlEffect effect) {
        super(effect);
    }

    @Override
    public AidFromTheCowlEffect copy() {
        return new AidFromTheCowlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);

            if (card != null) {
                if (new FilterPermanentCard().match(card, game) && controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " onto the battlefield?", source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else if (controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " on the bottom of your library?", source, game)) {
                    controller.putCardsOnBottomOfLibrary(cards, game, source, false);
                } else {
                    game.informPlayers(controller.getLogName() + " puts the revealed card back to the top of the library.");
                }
            }
        }
        return true;
    }
}
