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
package mage.cards.g;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.OpeningHandAction;
import mage.abilities.StaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.ConditionalManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class GemstoneCaverns extends CardImpl {

    public GemstoneCaverns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // If Gemstone Caverns is in your opening hand and you're not playing first, you may begin the game with Gemstone Caverns on the battlefield with a luck counter on it. If you do, exile a card from your hand.
        this.addAbility(new GemstoneCavernsAbility());

        // {T}: Add {C} to your mana pool. If Gemstone Caverns has a luck counter on it, instead add one mana of any color to your mana pool.
        Ability ability = new ConditionalManaAbility(Zone.BATTLEFIELD,
                new ConditionalManaEffect(
                        new AddManaOfAnyColorEffect(),
                        new BasicManaEffect(Mana.ColorlessMana(1)),
                        new SourceHasCounterCondition(CounterType.LUCK),
                        "Add {C} to your mana pool. If {this} has a luck counter on it, instead add one mana of any color to your mana pool."),
                new TapSourceCost());
        this.addAbility(ability);
    }

    public GemstoneCaverns(final GemstoneCaverns card) {
        super(card);
    }

    @Override
    public GemstoneCaverns copy() {
        return new GemstoneCaverns(this);
    }
}

class GemstoneCavernsAbility extends StaticAbility implements OpeningHandAction {

    public GemstoneCavernsAbility() {
        super(Zone.HAND, new GemstoneCavernsEffect());
    }

    public GemstoneCavernsAbility(final GemstoneCavernsAbility ability) {
        super(ability);
    }

    @Override
    public GemstoneCavernsAbility copy() {
        return new GemstoneCavernsAbility(this);
    }

    @Override
    public String getRule() {
        return "If {this} is in your opening hand and you're not playing first, you may begin the game with {this} on the battlefield with a luck counter on it. If you do, exile a card from your hand.";
    }

    @Override
    public boolean askUseOpeningHandAction(Card card, Player player, Game game) {
        return player.chooseUse(Outcome.PutCardInPlay, "Do you wish to put " + card.getIdName() + " into play?", this, game);
    }

    @Override
    public boolean isOpeningHandActionAllowed(Card card, Player player, Game game) {
        return !player.getId().equals(game.getStartingPlayerId());
    }

    @Override
    public void doOpeningHandAction(Card card, Player player, Game game) {
        this.resolve(game);
    }

}

class GemstoneCavernsEffect extends OneShotEffect {

    GemstoneCavernsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may begin the game with {this} on the battlefield with a luck counter on it. If you do, exile a card from your hand";
    }

    GemstoneCavernsEffect(final GemstoneCavernsEffect effect) {
        super(effect);
    }

    @Override
    public GemstoneCavernsEffect copy() {
        return new GemstoneCavernsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                if (card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), source.getControllerId())) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        permanent.addCounters(CounterType.LUCK.createInstance(), source, game);
                        Cost cost = new ExileFromHandCost(new TargetCardInHand());
                        if (cost.canPay(source, source.getSourceId(), source.getControllerId(), game)) {
                            cost.pay(source, game, source.getSourceId(), source.getControllerId(), true, null);
                        }
                    }
                }
            }
        }
        return false;
    }
}
