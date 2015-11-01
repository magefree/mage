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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class ArsenalThresher extends CardImpl {

    public ArsenalThresher(UUID ownerId) {
        super(ownerId, 131, "Arsenal Thresher", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W/B}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Construct");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Arsenal Thresher enters the battlefield, you may reveal any number of other artifact cards from your hand. Arsenal Thresher enters the battlefield with a +1/+1 counter on it for each card revealed this way.
        this.addAbility(new AsEntersBattlefieldAbility(new ArsenalThresherEffect(),
                "you may reveal any number of other artifact cards from your hand. {this} enters the battlefield with a +1/+1 counter on it for each card revealed this way"));
    }

    public ArsenalThresher(final ArsenalThresher card) {
        super(card);
    }

    @Override
    public ArsenalThresher copy() {
        return new ArsenalThresher(this);
    }
}

class ArsenalThresherEffect extends OneShotEffect {

    public ArsenalThresherEffect() {
        super(Outcome.Benefit);
    }

    public ArsenalThresherEffect(final ArsenalThresherEffect effect) {
        super(effect);
    }

    @Override
    public ArsenalThresherEffect copy() {
        return new ArsenalThresherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent arsenalThresher = game.getPermanentEntering(source.getSourceId());
        FilterArtifactCard filter = new FilterArtifactCard();
        filter.add(new AnotherCardPredicate());
        if (controller.chooseUse(Outcome.Benefit, "Do you want to reveal other artifacts in your hand?", source, game)) {
            Cards cards = new CardsImpl();
            if (controller.getHand().count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
                TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
                if (controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                    for (UUID uuid : target.getTargets()) {
                        cards.add(controller.getHand().get(uuid, game));
                    }
                    if (arsenalThresher != null) {
                        controller.revealCards(arsenalThresher.getIdName(), cards, game);
                        arsenalThresher.addCounters(CounterType.P1P1.createInstance(cards.size()), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
