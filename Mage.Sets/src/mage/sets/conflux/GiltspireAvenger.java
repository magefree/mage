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
package mage.sets.conflux;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerDamagedBySourceWatcher;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GiltspireAvenger extends CardImpl<GiltspireAvenger> {

    public GiltspireAvenger(UUID ownerId) {
        super(ownerId, 108, "Giltspire Avenger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        this.expansionSetCode = "CON";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.color.setBlue(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // {T}: Destroy target creature that dealt damage to you this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new GiltspireAvengerTarget());
        this.addAbility(ability);


    }

    public GiltspireAvenger(final GiltspireAvenger card) {
        super(card);
    }

    @Override
    public GiltspireAvenger copy() {
        return new GiltspireAvenger(this);
    }
}

class GiltspireAvengerTarget<T extends TargetCreaturePermanent<T>> extends TargetPermanent<TargetCreaturePermanent<T>> {

    public GiltspireAvengerTarget() {
        super(1, 1, new FilterCreaturePermanent(), false);
        targetName = "creature that dealt damage to you this turn";
    }

    public GiltspireAvengerTarget(final GiltspireAvengerTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource",source.getControllerId());
        if (watcher != null && watcher.damageSources.contains(id)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<UUID>();
        PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource", sourceControllerId);
        for (UUID targetId : availablePossibleTargets) {
            Permanent permanent = game.getPermanent(targetId);
            if(permanent != null && watcher != null && watcher.damageSources.contains(targetId)){
                possibleTargets.add(targetId);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets == 0)
                return true;
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get("PlayerDamagedBySource", sourceControllerId);
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
                if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)
                    && watcher != null && watcher.damageSources.contains(permanent.getId())) {
                        count++;
                        if (count >= remainingTargets)
                                return true;
                }
        }
        return false;
    }

    @Override
    public GiltspireAvengerTarget copy() {
        return new GiltspireAvengerTarget(this);
    }
}
