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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public class UlamogsDespoiler extends CardImpl {

    public UlamogsDespoiler(UUID ownerId) {
        super(ownerId, 16, "Ulamog's Despoiler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{6}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Eldrazi");
        this.subtype.add("Processor");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As Ulamog's Despoiler enters the battlefield, you may put two cards your opponents own from exile into their owners' graveyards. If you do, Ulamog's Despoiler enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new UlamogsDespoilerEffect(), null,
                "As {this} enters the battlefield, you may put two cards your opponents own from exile into their owners' graveyards. If you do, {this} enters the battlefield with four +1/+1 counters on it", null));
    }

    public UlamogsDespoiler(final UlamogsDespoiler card) {
        super(card);
    }

    @Override
    public UlamogsDespoiler copy() {
        return new UlamogsDespoiler(this);
    }
}

class UlamogsDespoilerEffect extends OneShotEffect {

    private final static FilterCard filter = new FilterCard("cards your opponents own from exile");

    static {
        filter.add(new OwnerPredicate(TargetController.OPPONENT));
    }

    public UlamogsDespoilerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put two cards your opponents own from exile into their owners' graveyards. If you do, {this} enters the battlefield with four +1/+1 counters on it";
    }

    public UlamogsDespoilerEffect(final UlamogsDespoilerEffect effect) {
        super(effect);
    }

    @Override
    public UlamogsDespoilerEffect copy() {
        return new UlamogsDespoilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInExile(2, 2, filter, null);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Cards cardsToGraveyard = new CardsImpl(target.getTargets());
                    controller.moveCards(cardsToGraveyard, Zone.GRAVEYARD, source, game);
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
