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
package mage.cards.n;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ControlledCreaturesDealCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class NaturesWill extends CardImpl {

    public NaturesWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Whenever one or more creatures you control deal combat damage to a player, tap all lands that player controls and untap all lands you control.
        this.addAbility(new ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(new NaturesWillEffect()));
    }

    public NaturesWill(final NaturesWill card) {
        super(card);
    }

    @Override
    public NaturesWill copy() {
        return new NaturesWill(this);
    }
}

class NaturesWillEffect extends OneShotEffect {

    public NaturesWillEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap all lands that player controls and untap all lands you control";
    }

    public NaturesWillEffect(final NaturesWillEffect effect) {
        super(effect);
    }

    @Override
    public NaturesWillEffect copy() {
        return new NaturesWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> damagedPlayers = (HashSet<UUID>) this.getValue("damagedPlayers");
        if (damagedPlayers != null) {
            List<Permanent> lands = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source.getSourceId(), game);
            for (Permanent land : lands) {
                if (damagedPlayers.contains(land.getControllerId())) {
                   land.tap(game);
                } else if (land.getControllerId().equals(source.getControllerId())) {
                    land.untap(game);
                }
            }
            return true;
        }
        return false;
    }
}
