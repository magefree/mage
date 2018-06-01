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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.ChooseFriendsAndFoes;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class PirsWhim extends CardImpl {

    public PirsWhim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // For each player, choose friend or foe. Each friend searches their library for a land card, puts it on the battlefield tapped, then shuffles their library. Each foe sacrifices an artifact or enchantment they control.
        this.getSpellAbility().addEffect(new PirsWhimEffect());
    }

    public PirsWhim(final PirsWhim card) {
        super(card);
    }

    @Override
    public PirsWhim copy() {
        return new PirsWhim(this);
    }
}

class PirsWhimEffect extends OneShotEffect {

    PirsWhimEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, choose friend or foe. "
                + "Each friend searches their library for a land card, "
                + "puts it onto the battlefield tapped, then shuffles their library. "
                + "Each foe sacrifices an artifact or enchantment they control.";
    }

    PirsWhimEffect(final PirsWhimEffect effect) {
        super(effect);
    }

    @Override
    public PirsWhimEffect copy() {
        return new PirsWhimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getSourceId());
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        if (!choice.chooseFriendOrFoe(controller, source, game)) {
            return false;
        }
        for (Player player : choice.getFriends()) {
            if (player != null) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_LAND);
                if (player.searchLibrary(target, game)) {
                    player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, true, null);
                    player.shuffleLibrary(source, game);
                }
            }
        }
        for (Player player : choice.getFoes()) {
            if (player != null) {
                Effect effect = new SacrificeEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, 1, "");
                effect.setTargetPointer(new FixedTarget(player.getId(), game));
                effect.apply(game, source);
            }
        }
        return true;
    }
}
