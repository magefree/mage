/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects.common.continious;

import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoostControlledEffect extends ContinuousEffectImpl<BoostControlledEffect> {

    private DynamicValue power;
    private DynamicValue toughness;
    protected FilterCreaturePermanent filter;
    protected boolean excludeSource;
    protected boolean lockedIn = false;

    public BoostControlledEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, new FilterCreaturePermanent("Creatures"), false);
    }

    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, new FilterCreaturePermanent("Creatures"), false);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, boolean excludeSource) {
        this(power, toughness, duration, new FilterCreaturePermanent("creatures"), excludeSource);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter) {
        this(new StaticValue(power), new StaticValue(toughness), duration, filter, false);
    }

    public BoostControlledEffect(int power, int toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(new StaticValue(power), new StaticValue(toughness), duration, filter, excludeSource);
    }

    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource) {
        this(power, toughness, duration, filter, excludeSource, false);
    }

    /**
     * @param lockedIn if true, power and toughness will be calculated only once, when the ability resolves
     */
    public BoostControlledEffect(DynamicValue power, DynamicValue toughness, Duration duration, FilterCreaturePermanent filter, boolean excludeSource, boolean lockedIn) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.filter = filter;
        this.excludeSource = excludeSource;
        this.lockedIn = lockedIn;
        setText();
    }

    public BoostControlledEffect(final BoostControlledEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
        this.lockedIn = effect.lockedIn;
    }

    @Override
    public BoostControlledEffect copy() {
        return new BoostControlledEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    objects.add(perm.getId());
                }
            }
        }
        if (this.lockedIn) {
            power = new StaticValue(power.calculate(game, source));
            toughness = new StaticValue(toughness.calculate(game, source));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    perm.addPower(power.calculate(game, source));
                    perm.addToughness(toughness.calculate(game, source));
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (excludeSource) {
            sb.append("Other ");
        }
        sb.append(filter.getMessage());
        sb.append(" you control get ");

        String p = power.toString();
        if(!p.startsWith("-")) {
            sb.append("+");
        }
        sb.append(p).append("/");
        String t = toughness.toString();
        if(!t.startsWith("-")){
            if(p.startsWith("-")) {
                sb.append("-");
            } else {
                sb.append("+");
            }
        }
        sb.append(t);

        sb.append((duration==Duration.EndOfTurn?" until end of turn":""));
        staticText = sb.toString();
    }

    public void setRule(String rule) {
        staticText = rule;
    }

    public void setLockedIn(boolean lockedIn) {
        this.lockedIn = lockedIn;
    }
}
