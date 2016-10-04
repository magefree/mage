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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author nantuko
 */
public class DjinnOfWishes extends CardImpl {

    private static final String ruleText = "{this} enters the battlefield with three wish counters on it";

    public DjinnOfWishes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add("Djinn");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // Djinn of Wishes enters the battlefield with three wish counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.WISH.createInstance(3)), ruleText));

        // {2}{U}{U}, Remove a wish counter from Djinn of Wishes: Reveal the top card of your library. You may play that card without paying its mana cost. If you don't, exile it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DjinnOfWishesEffect(), new ManaCostsImpl("{2}{U}{U}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.WISH.createInstance()));
        this.addAbility(ability);
    }

    public DjinnOfWishes(final DjinnOfWishes card) {
        super(card);
    }

    @Override
    public DjinnOfWishes copy() {
        return new DjinnOfWishes(this);
    }
}

class DjinnOfWishesEffect extends OneShotEffect {

    public DjinnOfWishesEffect() {
        super(Outcome.PlayForFree);
        staticText = "Reveal the top card of your library. You may play that card without paying its mana cost. If you don't, exile it";
    }

    public DjinnOfWishesEffect(final DjinnOfWishesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null && controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);
            if (!controller.chooseUse(Outcome.PlayForFree, "Play " + card.getName() + " without paying its mana cost?", source, game)
                    || !controller.playCard(card, game, true, true)) {
                card.moveToZone(Zone.EXILED, source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }

    @Override
    public DjinnOfWishesEffect copy() {
        return new DjinnOfWishesEffect(this);
    }

}
