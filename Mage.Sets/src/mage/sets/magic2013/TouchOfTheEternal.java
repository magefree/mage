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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class TouchOfTheEternal extends CardImpl<TouchOfTheEternal> {

    public TouchOfTheEternal(UUID ownerId) {
        super(ownerId, 37, "Touch of the Eternal", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{W}{W}");
        this.expansionSetCode = "M13";

        this.color.setWhite(true);

        // At the beginning of your upkeep, count the number of permanents you control. Your life total becomes that number.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new TouchOfTheEternalEffect(), Constants.TargetController.YOU, false));
    }

    public TouchOfTheEternal(final TouchOfTheEternal card) {
        super(card);
    }

    @Override
    public TouchOfTheEternal copy() {
        return new TouchOfTheEternal(this);
    }
}

class TouchOfTheEternalEffect extends OneShotEffect<TouchOfTheEternalEffect> {

    public TouchOfTheEternalEffect() {
        super(Constants.Outcome.Neutral);
        this.staticText = "count the number of permanents you control. Your life total becomes that number";
    }

    public TouchOfTheEternalEffect(final TouchOfTheEternalEffect effect) {
        super(effect);
    }

    @Override
    public TouchOfTheEternalEffect copy() {
        return new TouchOfTheEternalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledPermanent filter = new FilterControlledPermanent();
        Player player = game.getPlayer(source.getControllerId());
        int permanentsInPlay = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        if (player != null) {
            player.setLife(permanentsInPlay, game);
            return true;
        }
        return false;
    }
}
