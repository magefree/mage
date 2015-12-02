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

package mage.abilities.effects.common.continuous;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class GainAbilityAllEffect extends ContinuousEffectImpl {

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
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
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
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent permanent = it.next().getPermanentOrLKIBattlefield(game); //LKI is neccessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (permanent != null) {
                    permanent.addAbility(ability, source.getSourceId(), game, false);
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                    if (affectedObjectList.isEmpty()) {
                        discard();
                    }
                }
            }
        } else {
            for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    perm.addAbility(ability, source.getSourceId(), game, false);
                }
            }
            // still as long as the prev. permanent is known to the LKI (e.g. Mikaeus, the Unhallowed) so gained dies triggered ability will trigger
            HashMap<UUID, MageObject> LKIBattlefield = game.getLKI().get(Zone.BATTLEFIELD);
            if (LKIBattlefield != null) {
                for (MageObject mageObject: LKIBattlefield.values()) {
                    Permanent perm = (Permanent) mageObject;
                    if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                        if (filter.match(perm, source.getSourceId(), source.getControllerId(), game)) {
                            perm.addAbility(ability, source.getSourceId(), game, false);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        boolean quotes = (ability instanceof SimpleActivatedAbility) || (ability instanceof TriggeredAbility);
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
            if (filter.getMessage().toLowerCase().startsWith("each")) {
                sb.append(" gains ");
            } else {
                sb.append(" gain ");
            }            
        }
        if (quotes) {
            sb.append("\"");
        }
        sb.append(ability.getRule());
        if (quotes) {
            sb.append("\"");
        }
        if (duration.toString().length() > 0) {
            sb.append(" ").append(duration.toString());
        }
        staticText = sb.toString();
    }
}
