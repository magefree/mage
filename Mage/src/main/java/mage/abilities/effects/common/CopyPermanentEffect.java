/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.ApplyToPermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CopyPermanentEffect extends OneShotEffect {

    private FilterPermanent filter;
    private ApplyToPermanent applier;
    private Permanent bluePrintPermanent;
    private boolean useTargetOfAbility;

    public CopyPermanentEffect() {
        this(new FilterCreaturePermanent());
    }

    public CopyPermanentEffect(ApplyToPermanent applier) {
        this(StaticFilters.FILTER_PERMANENT_CREATURE, applier);
    }

    public CopyPermanentEffect(FilterPermanent filter) {
        this(filter, new EmptyApplyToPermanent());
    }

    public CopyPermanentEffect(FilterPermanent filter, ApplyToPermanent applier) {
        this(filter, applier, false);
    }

    public CopyPermanentEffect(FilterPermanent filter, ApplyToPermanent applier, boolean useTarget) {
        super(Outcome.Copy);
        this.applier = applier;
        this.filter = filter;
        this.useTargetOfAbility = useTarget;
        this.staticText = "as a copy of any " + filter.getMessage() + " on the battlefield";
    }

    public CopyPermanentEffect(final CopyPermanentEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.applier = effect.applier;
        this.bluePrintPermanent = effect.bluePrintPermanent;
        this.useTargetOfAbility = effect.useTargetOfAbility;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getPermanentEntering(source.getSourceId());
        if (sourceObject == null) {
            sourceObject = game.getObject(source.getSourceId());
        }
        if (player != null && sourceObject != null) {
            Permanent copyFromPermanent = null;
            if (useTargetOfAbility) {
                copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            } else {
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(source.getSourceId(), player.getId(), game)) {
                    player.choose(Outcome.Copy, target, source.getSourceId(), game);
                    copyFromPermanent = game.getPermanent(target.getFirstTarget());
                }
            }
            if (copyFromPermanent != null) {
                bluePrintPermanent = game.copyPermanent(copyFromPermanent, sourceObject.getId(), source, applier);
            }
            return true;
        }
        return false;
    }

    public Permanent getBluePrintPermanent() {
        return bluePrintPermanent;
    }

    @Override
    public CopyPermanentEffect copy() {
        return new CopyPermanentEffect(this);
    }
}
