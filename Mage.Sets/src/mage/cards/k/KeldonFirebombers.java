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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class KeldonFirebombers extends CardImpl {

    public KeldonFirebombers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Keldon Firebombers enters the battlefield, each player sacrifices all lands he or she controls except for three.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KeldonFirebombersEffect()));

    }

    public KeldonFirebombers(final KeldonFirebombers card) {
        super(card);
    }

    @Override
    public KeldonFirebombers copy() {
        return new KeldonFirebombers(this);
    }
}

class KeldonFirebombersEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    public KeldonFirebombersEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "each player sacrifices all lands he or she controls except for three";
    }

    public KeldonFirebombersEffect(final KeldonFirebombersEffect effect) {
        super(effect);
    }

    @Override
    public KeldonFirebombersEffect copy() {
        return new KeldonFirebombersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> landsToSacrifice = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int amount = game.getBattlefield().getAllActivePermanents(filter, playerId, game).size() - 3;
                if (amount > 0) {
                    FilterLandPermanent playerFilter = filter.copy();
                    playerFilter.add(new ControllerIdPredicate(playerId));
                    Target target = new TargetLandPermanent(amount, amount, playerFilter, true);
                    player.choose(outcome.Sacrifice, target, source.getSourceId(), game);
                    for (UUID landId : target.getTargets()) {
                        Permanent land = game.getPermanent(landId);
                        if (land != null) {
                            landsToSacrifice.add(land);
                        }
                    }
                }
            }
        }
        for (Permanent land : landsToSacrifice) {
            land.sacrifice(source.getSourceId(), game);
        }
        return true;
    }
}
