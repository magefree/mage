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
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class KeeperOfTheMind extends CardImpl {

    public KeeperOfTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}, {tap}: Choose target opponent who had at least two more cards in hand than you did as you activated this ability. Draw a card.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("Choose target opponent who had at least two more cards in hand than you did as you activated this ability. Draw a card.");
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.setTargetAdjuster(KeeperOfTheMindAdjuster.instance);
        this.addAbility(ability);
    }

    private KeeperOfTheMind(final KeeperOfTheMind card) {
        super(card);
    }

    @Override
    public KeeperOfTheMind copy() {
        return new KeeperOfTheMind(this);
    }
}

enum KeeperOfTheMindAdjuster implements TargetAdjuster {
    instance;

    private static final FilterOpponent filter = new FilterOpponent();

    static {
        filter.add(new KeeperOfTheMindPredicate());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer == null) {
            return;
        }
        ability.getTargets().clear();
        TargetPlayer target = new TargetPlayer(1, 1, false, filter);
        target.setTargetController(activePlayer.getId());
        ability.addTarget(target);
    }
}

class KeeperOfTheMindPredicate implements ObjectSourcePlayerPredicate<Player> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        if (targetPlayer == null
                || firstPlayer == null
                || !firstPlayer.hasOpponent(targetPlayer.getId(), game)) {
            return false;
        }
        int countHandTargetPlayer = targetPlayer.getHand().size();
        int countHandFirstPlayer = firstPlayer.getHand().size();

        return countHandTargetPlayer - 2 >= countHandFirstPlayer;
    }

    @Override
    public String toString() {
        return "opponent who had at least two more cards in hand than you did as you activated this ability";
    }
}
