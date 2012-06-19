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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterNonTokenPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public class FeedThePack extends CardImpl<FeedThePack> {

    public FeedThePack(UUID ownerId) {
        super(ownerId, 114, "Feed the Pack", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}");
        this.expansionSetCode = "DKA";

        this.color.setGreen(true);

        // At the beginning of your end step, you may sacrifice a nontoken creature. If you do, put X 2/2 green Wolf creature tokens onto the battlefield, where X is the sacrificed creature's toughness.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new FeedThePackEffect(), true));
    }

    public FeedThePack(final FeedThePack card) {
        super(card);
    }

    @Override
    public FeedThePack copy() {
        return new FeedThePack(this);
    }
}

class FeedThePackEffect extends OneShotEffect<FeedThePackEffect> {

    private static final FilterNonTokenPermanent filter = new FilterNonTokenPermanent("nontoken creature");

    static {
        filter.getCardType().add(CardType.CREATURE);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
        filter.setTargetController(Constants.TargetController.YOU);
    }

    public FeedThePackEffect() {
        super(Constants.Outcome.Sacrifice);
        this.staticText = "sacrifice a nontoken creature. If you do, put X 2/2 green Wolf creature tokens onto the battlefield, where X is the sacrificed creature's toughness";
    }

    public FeedThePackEffect(final FeedThePackEffect effect) {
        super(effect);
    }

    @Override
    public FeedThePackEffect copy() {
        return new FeedThePackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = new TargetPermanent(filter);
        Player player = game.getPlayer(source.getControllerId());
        if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null && permanent.sacrifice(source.getSourceId(), game)) {
                int toughness = permanent.getToughness().getValue();
                WolfToken token = new WolfToken();
                token.putOntoBattlefield(toughness, game, source.getSourceId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}