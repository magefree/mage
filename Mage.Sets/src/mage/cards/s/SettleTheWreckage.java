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
package mage.cards.s;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public class SettleTheWreckage extends CardImpl {

    public SettleTheWreckage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Exile all attacking creatures target player controls. That player may search their library for that many basic land cards, put those cards onto the battlefield tapped, then shuffle their library.
        this.getSpellAbility().addEffect(new SettleTheWreckageEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public SettleTheWreckage(final SettleTheWreckage card) {
        super(card);
    }

    @Override
    public SettleTheWreckage copy() {
        return new SettleTheWreckage(this);
    }
}

class SettleTheWreckageEffect extends OneShotEffect {

    SettleTheWreckageEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile all attacking creatures target player controls. That player may search their library for that many basic land cards, put those cards onto the battlefield tapped, then shuffle their library";
    }

    SettleTheWreckageEffect(final SettleTheWreckageEffect effect) {
        super(effect);
    }

    @Override
    public SettleTheWreckageEffect copy() {
        return new SettleTheWreckageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || player == null) {
            return false;
        }
        int attackers = 0;
        Set<Card> toExile = new HashSet<>();
        Iterator<UUID> creatureIds = game.getCombat().getAttackers().iterator();
        while (creatureIds.hasNext()) {
            Permanent creature = game.getPermanent(creatureIds.next());
            if (creature != null && creature.getControllerId().equals(player.getId())) {
                toExile.add(creature);
                attackers++;
            }
        }
        controller.moveCards(toExile, Zone.EXILED, source, game);
        TargetCardInLibrary target = new TargetCardInLibrary(0, attackers, StaticFilters.FILTER_BASIC_LAND_CARD);
        if (player.chooseUse(Outcome.Benefit, "Search for up to " + attackers + " basic land" + ((attackers == 1) ? "" : "s") + "?", source, game) && player.searchLibrary(target, game)) {
            player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
            player.shuffleLibrary(source, game);
        }
        return true;

    }
}
