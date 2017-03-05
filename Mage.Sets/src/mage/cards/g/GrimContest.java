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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GrimContest extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GrimContest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{G}");

        // Choose target creature you control and target creature an opponent controls. Each of those creatures deals damage equal to its toughness to the other.
        this.getSpellAbility().addEffect(new GrimContestEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public GrimContest(final GrimContest card) {
        super(card);
    }

    @Override
    public GrimContest copy() {
        return new GrimContest(this);
    }
}

class GrimContestEffect extends OneShotEffect {

    public GrimContestEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose target creature you control and target creature an opponent controls. Each of those creatures deals damage equal to its toughness to the other";
    }

    public GrimContestEffect(final GrimContestEffect effect) {
        super(effect);
    }

    @Override
    public GrimContestEffect copy() {
        return new GrimContestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent creature1 = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (creature1 != null && creature2 != null) {
                if (creature1.isCreature() && creature2.isCreature()) {
                    creature1.damage(creature2.getToughness().getValue(), creature2.getId(), game, false, true);
                    game.informPlayers(creature2.getLogName() + " deals " + creature2.getToughness().getValue() + " damage to " + creature1.getLogName());
                    creature2.damage(creature1.getToughness().getValue(), creature1.getId(), game, false, true);
                    game.informPlayers(creature1.getLogName() + " deals " + creature1.getToughness().getValue() + " damage to " + creature2.getLogName());
                }
            }
            return true;
        }
        return false;
    }
}
