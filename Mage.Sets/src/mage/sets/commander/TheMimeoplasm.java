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
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public class TheMimeoplasm extends CardImpl {

    public TheMimeoplasm(UUID ownerId) {
        super(ownerId, 210, "The Mimeoplasm", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{B}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Ooze");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As The Mimeoplasm enters the battlefield, you may exile two creature cards from graveyards. If you do, it enters the battlefield as a copy of one of those cards with a number of additional +1/+1 counters on it equal to the power of the other card.
        this.addAbility(new AsEntersBattlefieldAbility(new TheMimeoplasmEffect(), "you may exile two creature cards from graveyards. If you do, it enters the battlefield as a copy of one of those cards with a number of additional +1/+1 counters on it equal to the power of the other card"));
    }

    public TheMimeoplasm(final TheMimeoplasm card) {
        super(card);
    }

    @Override
    public TheMimeoplasm copy() {
        return new TheMimeoplasm(this);
    }
}

class TheMimeoplasmEffect extends OneShotEffect {

    TheMimeoplasmEffect() {
        super(Outcome.Copy);
    }

    TheMimeoplasmEffect(final TheMimeoplasmEffect effect) {
        super(effect);
    }

    @Override
    public TheMimeoplasmEffect copy() {
        return new TheMimeoplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && permanent != null) {
            if (new CardsInAllGraveyardsCount(new FilterCreatureCard()).calculate(game, source, this) >= 2) {
                if (controller.chooseUse(Outcome.Benefit, "Do you want to exile two creature cards from graveyards?", source, game)) {
                    TargetCardInGraveyard targetCopy = new TargetCardInGraveyard(new FilterCreatureCard("creature card to become a copy of"));
                    TargetCardInGraveyard targetCounters = new TargetCardInGraveyard(new FilterCreatureCard("creature card to determine amount of additional +1/+1 counters"));
                    if (controller.choose(Outcome.Copy, targetCopy, source.getSourceId(), game)) {
                        Card cardToCopy = game.getCard(targetCopy.getFirstTarget());
                        if (cardToCopy != null) {
                            if (controller.choose(Outcome.Copy, targetCounters, source.getSourceId(), game)) {
                                Card cardForCounters = game.getCard(targetCounters.getFirstTarget());
                                if (cardForCounters != null) {
                                    Cards cardsToExile = new CardsImpl();
                                    cardsToExile.add(cardToCopy);
                                    cardsToExile.add(cardForCounters);
                                    controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                                    CopyEffect copyEffect = new CopyEffect(Duration.Custom, cardToCopy, source.getSourceId());
                                    game.addEffect(copyEffect, source);
                                    permanent.addCounters(CounterType.P1P1.createInstance(cardForCounters.getPower().getValue()), game);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
