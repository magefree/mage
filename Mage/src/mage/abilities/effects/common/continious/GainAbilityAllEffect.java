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

package mage.abilities.effects.common.continious;

import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class GainAbilityAllEffect extends ContinuousEffectImpl<GainAbilityAllEffect> {

    protected Ability ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;

    public GainAbilityAllEffect(Ability ability, Duration duration) {
        this(ability, duration, new FilterPermanent());
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, String text) {
        this(ability, duration, filter, false);
        this.staticText = text;
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        this.excludeSource = excludeSource;
        setText();
    }

    public GainAbilityAllEffect(final GainAbilityAllEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    objects.add(perm.getId());
                }
            }
        }
    }

    @Override
    public GainAbilityAllEffect copy() {
        return new GainAbilityAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    perm.addAbility(ability, source.getSourceId(), game);
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
        if (duration.equals(Duration.WhileOnBattlefield)) {
            if (filter.getMessage().toLowerCase().startsWith("each")) {
                sb.append(" has ");
            } else {
                sb.append(" have ");
            }
        } else {
            sb.append(" gain ");
        }
        sb.append(ability.getRule());
        if (duration.toString().length() > 0) {
            sb.append(" ").append(duration.toString());
        }
        staticText = sb.toString();
    }
}
