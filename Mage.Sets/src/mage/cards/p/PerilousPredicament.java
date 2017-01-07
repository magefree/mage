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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class PerilousPredicament extends CardImpl {

    public PerilousPredicament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Each opponent sacrifices an artifact and a nonartifact creature.
        getSpellAbility().addEffect(new PerilousPredicamentSacrificeOpponentsEffect());
    }

    public PerilousPredicament(final PerilousPredicament card) {
        super(card);
    }

    @Override
    public PerilousPredicament copy() {
        return new PerilousPredicament(this);
    }
}

class PerilousPredicamentSacrificeOpponentsEffect extends OneShotEffect {

    public PerilousPredicamentSacrificeOpponentsEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each opponent sacrifices an artifact and a nonartifact creature";
    }

    public PerilousPredicamentSacrificeOpponentsEffect(final PerilousPredicamentSacrificeOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public PerilousPredicamentSacrificeOpponentsEffect copy() {
        return new PerilousPredicamentSacrificeOpponentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                FilterArtifactCreaturePermanent filterArtifact = new FilterArtifactCreaturePermanent("an artifact creature");
                filterArtifact.add(new ControllerIdPredicate(player.getId()));
                FilterCreaturePermanent filterNonArtifact = new FilterCreaturePermanent("an non artifact creature");
                filterNonArtifact.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
                filterNonArtifact.add(new ControllerIdPredicate(player.getId()));
                if (game.getBattlefield().countAll(filterArtifact, player.getId(), game) > 0) {
                    TargetPermanent target = new TargetPermanent(1, 1, filterArtifact, true);
                    if (target.canChoose(player.getId(), game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
                if (game.getBattlefield().countAll(filterNonArtifact, player.getId(), game) > 0) {
                    TargetPermanent target = new TargetPermanent(1, 1, filterNonArtifact, true);
                    if (target.canChoose(player.getId(), game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }

                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        return true;
    }
}
