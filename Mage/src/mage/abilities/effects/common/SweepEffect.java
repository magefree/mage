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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class SweepEffect extends OneShotEffect<SweepEffect> {

    private String sweepSubtype;

    public SweepEffect(String sweepSubtype) {
        super(Outcome.Benefit);
        this.sweepSubtype = sweepSubtype;
        this.staticText = new StringBuilder("<i>Sweep</i> - Return any number of ").append(sweepSubtype).append(sweepSubtype.endsWith("s")?"":"s").append(" you control to their owner's hand").toString();
    }

    public SweepEffect(final SweepEffect effect) {
        super(effect);
        this.sweepSubtype = effect.sweepSubtype;
    }

    @Override
    public SweepEffect copy() {
        return new SweepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterPermanent filter = new FilterControlledLandPermanent(new StringBuilder("any number of ").append(sweepSubtype).append("s you control").toString());
            filter.add(new SubtypePredicate(sweepSubtype));
            Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
            target.setRequired(true);
            if (controller.chooseTarget(outcome, target, source, game)) {
                game.getState().setValue(CardUtil.getCardZoneString("sweep", source.getSourceId(), game), target.getTargets().size());
                for (UUID uuid : target.getTargets()) {
                    Permanent land = game.getPermanent(uuid);
                    land.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
}
