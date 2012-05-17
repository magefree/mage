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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class DescentIntoMadness extends CardImpl<DescentIntoMadness> {

    public DescentIntoMadness(UUID ownerId) {
        super(ownerId, 97, "Descent into Madness", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "AVR";

        this.color.setBlack(true);

        // At the beginning of your upkeep, put a despair counter on Descent into Madness, then each player exiles X permanents he or she controls and/or cards from his or her hand, where X is the number of despair counters on Descent into Madness.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DescentIntoMadnessEffect(), Constants.TargetController.YOU, false));
    }

    public DescentIntoMadness(final DescentIntoMadness card) {
        super(card);
    }

    @Override
    public DescentIntoMadness copy() {
        return new DescentIntoMadness(this);
    }
}

class DescentIntoMadnessEffect extends OneShotEffect<DescentIntoMadnessEffect> {

    private static final FilterCard filterInHand = new FilterCard();
    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    public DescentIntoMadnessEffect() {
        super(Constants.Outcome.Sacrifice);
        this.staticText = "put a despair counter on {this}, then each player exiles X permanents he or she controls and/or cards from his or her hand, where X is the number of despair counters on {this}";
    }

    public DescentIntoMadnessEffect(final DescentIntoMadnessEffect effect) {
        super(effect);
    }

    @Override
    public DescentIntoMadnessEffect copy() {
        return new DescentIntoMadnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.DESPAIR.createInstance(), game);
            int count = permanent.getCounters().getCount(CounterType.DESPAIR);
            if (count > 0) {

                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    for (UUID playerId : controller.getInRange()) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            exileCards(player, count, source, game);
                        }
                    }
                }
            }
        }
        return false;
    }

    private void exileCards(Player player, int count, Ability source, Game game) {
        int amount = Math.min(count, player.getHand().size() + game.getBattlefield().getAllActivePermanents(player.getId()).size());

        while (amount > 0) {
            Target target = new TargetControlledPermanent(0, 1, filter, true);
            if (target.canChoose(player.getId(), game)
                    && player.choose(Constants.Outcome.Exile, target, source.getSourceId(), game)) {

                for (UUID targetId : target.getTargets()) {
                    Permanent chosen = game.getPermanent(targetId);
                    if (chosen != null) {
                        chosen.moveToExile(source.getId(), "Descent into Madness", source.getSourceId(), game);
                        amount--;
                    }
                }
            }

            if (amount > 0) {
                TargetCard targetInHand = new TargetCard(Constants.Zone.HAND, filterInHand);
                if (targetInHand.canChoose(player.getId(), game) &&
                        player.choose(Constants.Outcome.Exile, player.getHand(), targetInHand, game)) {

                    Card card = player.getHand().get(targetInHand.getFirstTarget(), game);
                    if (card != null) {
                        card.moveToExile(source.getId(), "Descent into Madness", source.getSourceId(), game);
                        amount--;
                    }
                }
            }
        }
    }
}
