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
package mage.sets.darksteel;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public class AEtherVial extends CardImpl<AEtherVial> {

    public AEtherVial(UUID ownerId) {
        super(ownerId, 91, "AEther Vial", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "DST";

        // At the beginning of your upkeep, you may put a charge counter on AEther Vial.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), TargetController.YOU, true));
        // {tap}: You may put a creature card with converted mana cost equal to the number of charge counters on AEther Vial from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AEtherVialEffect(), new TapSourceCost()));
    }

    public AEtherVial(final AEtherVial card) {
        super(card);
    }

    @Override
    public AEtherVial copy() {
        return new AEtherVial(this);
    }
}

class AEtherVialEffect extends OneShotEffect<AEtherVialEffect> {

    public AEtherVialEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a creature card with converted mana cost equal to the number of charge counters on {this} from your hand onto the battlefield";
    }

    public AEtherVialEffect(final AEtherVialEffect effect) {
        super(effect);
    }

    @Override
    public AEtherVialEffect copy() {
        return new AEtherVialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (permanent == null) {
                return false;
            }
        }
        int count = permanent.getCounters().getCount(CounterType.CHARGE);

        FilterCreatureCard filter = new FilterCreatureCard("creature card with converted mana cost equal to " + count);
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, count));
        String choiceText = "Put a " + filter.getMessage() + " from your hand onto the battlefield?";

        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().count(filter, game) == 0
                || !player.chooseUse(this.outcome, choiceText, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(this.outcome, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
