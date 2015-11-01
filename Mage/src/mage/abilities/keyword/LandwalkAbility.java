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
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LandwalkAbility extends EvasionAbility {

    public LandwalkAbility(FilterLandPermanent filter) {
        this(filter, true);
    }

    public LandwalkAbility(FilterLandPermanent filter, boolean withHintText) {
        this.addEffect(new LandwalkEffect(filter, withHintText));
    }

    public LandwalkAbility(final LandwalkAbility ability) {
        super(ability);
    }

    @Override
    public LandwalkAbility copy() {
        return new LandwalkAbility(this);
    }

    @Override
    public String getRule() {
        String ruleText = super.getRule();
        if (!ruleText.isEmpty() && ruleText.endsWith(".")) {
            return ruleText.substring(0, ruleText.length() - 1);
        }
        return ruleText;
    }

}

class LandwalkEffect extends RestrictionEffect {

    protected FilterLandPermanent filter;

    public LandwalkEffect(FilterLandPermanent filter, boolean withHintText) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = setText(withHintText);
    }

    public LandwalkEffect(final LandwalkEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return !game.getBattlefield().contains(filter, blocker.getControllerId(), 1, game);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public LandwalkEffect copy() {
        return new LandwalkEffect(this);
    }

    private String setText(boolean withHintText) {
        // Swampwalk (This creature can't be blocked as long as defending player controls a Swamp.)
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append("walk");
        if (withHintText) {
            sb.append(" <i>(This creature can't be blocked as long as defending player controls a ");
            switch (filter.getMessage()) {
                case "swamp":
                    sb.append("Swamp");
                    break;
                case "plains":
                    sb.append("Plains");
                    break;
                case "mountain":
                    sb.append("Mountain");
                    break;
                case "forest":
                    sb.append("Forest");
                    break;
                case "island":
                    sb.append("Island");
                    break;
                default:
                    sb.append(filter.getMessage());

            }
            sb.append(".)</i>");
        }
        return sb.toString();
    }
}
