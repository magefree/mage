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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author nantuko
 */

public class UntapAllControllerEffect extends OneShotEffect {

    private final FilterPermanent filter;
    private final boolean includeSource;

    public UntapAllControllerEffect(FilterPermanent filter) {
        this(filter, null);
    }

    public UntapAllControllerEffect(FilterPermanent filter, String rule) {
        this(filter, rule, true);
    }

    public UntapAllControllerEffect(FilterPermanent filter, String rule, boolean includeSource) {
        super(Outcome.Untap);
        if (rule == null || rule.isEmpty()) {
            staticText = "untap all " + (includeSource ? "" : "other ") + filter.getMessage() + " you control";
        } else {
            staticText = rule;
        }
        this.filter = filter;
        this.includeSource = includeSource;
    }

    public UntapAllControllerEffect(final UntapAllControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.includeSource = effect.includeSource;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null) {
            for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                if (includeSource || sourcePermanent == null || !sourcePermanent.getId().equals(permanent.getId())) {
                    permanent.untap(game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllControllerEffect copy() {
        return new UntapAllControllerEffect(this);
    }

}
