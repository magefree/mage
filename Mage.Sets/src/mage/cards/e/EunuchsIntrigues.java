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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801 & L_J
 */
public class EunuchsIntrigues extends CardImpl {

    public EunuchsIntrigues(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Target opponent chooses a creature he or she controls. Other creatures he or she controls can't block this turn.
        this.getSpellAbility().addEffect(new EunuchsIntriguesEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public EunuchsIntrigues(final EunuchsIntrigues card) {
        super(card);
    }

    @Override
    public EunuchsIntrigues copy() {
        return new EunuchsIntrigues(this);
    }
}

class EunuchsIntriguesEffect extends OneShotEffect {

    EunuchsIntriguesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent chooses a creature he or she controls. Other creatures he or she controls can't block this turn.";
    }

    EunuchsIntriguesEffect(final EunuchsIntriguesEffect effect) {
        super(effect);
    }

    @Override
    public EunuchsIntriguesEffect copy() {
        return new EunuchsIntriguesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
        filter.add(new ControllerIdPredicate(player.getId()));
        Target target = new TargetPermanent(1, 1, filter, true);
        if (target.canChoose(source.getSourceId(), player.getId(), game)) {
            while (!target.isChosen() && target.canChoose(player.getId(), game) && player.canRespond()) {
                player.chooseTarget(Outcome.DestroyPermanent, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                game.informPlayers(player.getLogName() + " has chosen " + permanent.getLogName() + " as his only creature able to block this turn");
            }
        }
        game.addEffect(new EunuchsIntriguesRestrictionEffect(target.getFirstTarget()), source);
        return true;
    }
}

class EunuchsIntriguesRestrictionEffect extends RestrictionEffect {
    
    protected UUID targetId;

    public EunuchsIntriguesRestrictionEffect(UUID targetId) {
        super(Duration.EndOfTurn);
        this.targetId = targetId;
    }

    public EunuchsIntriguesRestrictionEffect(final EunuchsIntriguesRestrictionEffect effect) {
        super(effect);
        targetId = effect.targetId;
    }

    @Override
    public EunuchsIntriguesRestrictionEffect copy() {
        return new EunuchsIntriguesRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getControllerId().equals(source.getFirstTarget())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (targetId != null && blocker.getId().equals(targetId)) {
            return true;
        }
        return false;
    }
}
