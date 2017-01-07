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

    import java.util.HashSet;
    import java.util.Set;
    import java.util.UUID;
    import mage.constants.Zone;
    import mage.MageObject;
    import mage.abilities.Ability;
    import mage.abilities.dynamicvalue.DynamicValue;
    import mage.abilities.dynamicvalue.common.StaticValue;
    import mage.filter.Filter;
    import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
    import mage.game.Game;
    import mage.game.permanent.Permanent;
    import mage.target.TargetAmount;

    /**
     *
     * @author BetaSteward_at_googlemail.com
     */
    public class TargetCreatureOrPlaneswalkerAmount extends TargetAmount {

        protected final FilterCreatureOrPlaneswalkerPermanent filter;

        public TargetCreatureOrPlaneswalkerAmount(int amount) {
            // 107.1c If a rule or ability instructs a player to choose “any number,” that player may choose
            // any positive number or zero, unless something (such as damage or counters) is being divided
            // or distributed among “any number” of players and/or objects. In that case, a nonzero number
            // of players and/or objects must be chosen if possible.
            this(amount, new FilterCreatureOrPlaneswalkerPermanent());
        }

        public TargetCreatureOrPlaneswalkerAmount(DynamicValue amount) {
            this(amount, new FilterCreatureOrPlaneswalkerPermanent());
        }

        public TargetCreatureOrPlaneswalkerAmount(int amount, FilterCreatureOrPlaneswalkerPermanent filter) {
            this(new StaticValue(amount), filter);
        }

        public TargetCreatureOrPlaneswalkerAmount(DynamicValue amount, FilterCreatureOrPlaneswalkerPermanent filter) {
            super(amount);
            this.zone = Zone.ALL;
            this.filter = filter;
            this.targetName = filter.getMessage();
        }

        public TargetCreatureOrPlaneswalkerAmount(final TargetCreatureOrPlaneswalkerAmount target) {
            super(target);
            this.filter = target.filter.copy();
        }

        @Override
        public Filter getFilter() {
            return this.filter;
        }

        @Override
        public boolean canTarget(UUID objectId, Game game) {
            Permanent permanent = game.getPermanent(objectId);
            return permanent != null && filter.match(permanent, game);
        }

        @Override
        public boolean canTarget(UUID objectId, Ability source, Game game) {
            Permanent permanent = game.getPermanent(objectId);
            if (permanent != null) {
                if (source != null) {
                    MageObject targetSource = source.getSourceObject(game);
                    return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game) && filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
                } else {
                    return filter.match(permanent, game);
                }
            }
            return false;
        }

        @Override
        public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
            return canTarget(objectId, source, game);
        }

        @Override
        public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
            int count = 0;
            MageObject targetSource = game.getObject(sourceId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreatureOrPlaneswalkerPermanent(), sourceControllerId, game)) {
                if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canChoose(UUID sourceControllerId, Game game) {
            int count = 0;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreatureOrPlaneswalkerPermanent(), sourceControllerId, game)) {
                if (filter.match(permanent, null, sourceControllerId, game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
            Set<UUID> possibleTargets = new HashSet<>();
            MageObject targetSource = game.getObject(sourceId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreatureOrPlaneswalkerPermanent(), sourceControllerId, game)) {
                if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                    possibleTargets.add(permanent.getId());
                }
            }
            return possibleTargets;
        }

        @Override
        public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
            Set<UUID> possibleTargets = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreatureOrPlaneswalkerPermanent(), sourceControllerId, game)) {
                if (filter.match(permanent, null, sourceControllerId, game)) {
                    possibleTargets.add(permanent.getId());
                }
            }
            return possibleTargets;
        }

        @Override
        public String getTargetedName(Game game) {
            StringBuilder sb = new StringBuilder();
            for (UUID targetId : getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    sb.append(permanent.getLogName()).append("(").append(getTargetAmount(targetId)).append(") ");
                }
            }
            return sb.toString();
        }

        @Override
        public TargetCreatureOrPlaneswalkerAmount copy() {
            return new TargetCreatureOrPlaneswalkerAmount(this);
        }

    }
