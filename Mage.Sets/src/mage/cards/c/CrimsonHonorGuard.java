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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class CrimsonHonorGuard extends CardImpl {

    public CrimsonHonorGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add("Vampire");
        this.subtype.add("Knight");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each player's end step, Crimson Honor Guard deals 4 damage to that player unless he or she controls a commander.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CrimsonHonorGuardEffect(), TargetController.ANY, false));
    }

    public CrimsonHonorGuard(final CrimsonHonorGuard card) {
        super(card);
    }

    @Override
    public CrimsonHonorGuard copy() {
        return new CrimsonHonorGuard(this);
    }
}

class CrimsonHonorGuardEffect extends OneShotEffect {

    private final static FilterPermanent filter = new FilterPermanent("Commander");

    static {
        filter.add(new CommanderPredicate());
    }

    public CrimsonHonorGuardEffect() {
        super(Outcome.Damage);
    }

    @Override
    public Effect copy() {
        return new CrimsonHonorGuardEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            int numCommanders = game.getBattlefield().getAllActivePermanents(filter, player.getId(), game).size();
            if (numCommanders == 0) {
                player.damage(4, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "{this} deals 4 damage to that player unless he or she controls a commander";
    }
}
