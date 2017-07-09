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
package mage.target.common;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetObject;

public class TargetActivatedOrTriggeredAbility extends TargetObject {

    protected final FilterStackObject filter;

    public TargetActivatedOrTriggeredAbility() {
        this(new FilterStackObject());
    }

    public TargetActivatedOrTriggeredAbility(FilterStackObject filter) {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = filter.getMessage();
        this.filter = filter;
    }

    public TargetActivatedOrTriggeredAbility(final TargetActivatedOrTriggeredAbility target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        // rule 114.4. A spell or ability on the stack is an illegal target for itself.
        if (source != null && source.getId().equals(id)) {
            return false;
        }

        StackObject stackObject = game.getStack().getStackObject(id);
        return isActivatedOrTriggeredAbility(stackObject) && filter.match(stackObject, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if (isActivatedOrTriggeredAbility(stackObject)
                    && filter.match(stackObject, sourceId, sourceControllerId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return game.getStack()
                .stream()
                .anyMatch(TargetActivatedOrTriggeredAbility::isActivatedOrTriggeredAbility);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return game.getStack().stream()
                .filter(TargetActivatedOrTriggeredAbility::isActivatedOrTriggeredAbility)
                .map(stackObject -> stackObject.getStackAbility().getId())
                .collect(Collectors.toSet());
    }

    @Override
    public TargetActivatedOrTriggeredAbility copy() {
        return new TargetActivatedOrTriggeredAbility(this);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    static boolean isActivatedOrTriggeredAbility(StackObject stackObject) {
        if (stackObject == null) {
            return false;
        }
        if (stackObject instanceof Ability) {
            Ability ability = (Ability) stackObject;
            return ability.getAbilityType() == AbilityType.TRIGGERED
                    || ability.getAbilityType() == AbilityType.ACTIVATED;
        }
        return false;
    }
}
