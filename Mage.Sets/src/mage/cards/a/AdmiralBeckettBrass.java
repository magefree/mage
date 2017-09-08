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
package mage.cards.a;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public class AdmiralBeckettBrass extends CardImpl {

    private final UUID originalId;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other Pirates you control");

    static {
        filter.add(new SubtypePredicate(SubType.PIRATE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public AdmiralBeckettBrass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Pirates you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // At the beginning of your end step, gain control of target nonland permanent controlled by a player who was dealt combat damage by three or more Pirates this turn.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new GainControlTargetEffect(Duration.Custom), TargetController.YOU, false);
        ability.addTarget(new TargetNonlandPermanent());
        originalId = ability.getOriginalId();
        this.addAbility(ability, new DamagedByPiratesWatcher());
    }

    public AdmiralBeckettBrass(final AdmiralBeckettBrass card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            FilterNonlandPermanent playerFilter = new FilterNonlandPermanent("nonland permanent controlled by a player who was dealt combat damage by three or more Pirates this turn");
            playerFilter.add(new ControllerDealtDamageByPiratesPredicate());
            TargetNonlandPermanent target = new TargetNonlandPermanent(1, 1, playerFilter, true);
            ability.addTarget(target);
        }
    }

    @Override
    public AdmiralBeckettBrass copy() {
        return new AdmiralBeckettBrass(this);
    }
}

class DamagedByPiratesWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> damageSourceIds = new HashMap<>();

    public DamagedByPiratesWatcher() {
        super(DamagedByPiratesWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public DamagedByPiratesWatcher(final DamagedByPiratesWatcher watcher) {
        super(watcher);
        for (UUID playerId : watcher.damageSourceIds.keySet()) {
            Set<UUID> creatures = new HashSet<>();
            creatures.addAll(watcher.damageSourceIds.get(playerId));
            this.damageSourceIds.put(playerId, creatures);
        }
    }

    @Override
    public DamagedByPiratesWatcher copy() {
        return new DamagedByPiratesWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getFlag()) {
            Permanent creature = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (creature != null && creature.getSubtype(game).contains(SubType.PIRATE)) {
                if (damageSourceIds.keySet().contains(event.getTargetId())) {
                    damageSourceIds.get(event.getTargetId()).add(creature.getId());
                } else {
                    Set<UUID> creatureSet = new HashSet();
                    creatureSet.add(creature.getId());
                    damageSourceIds.put(event.getTargetId(), creatureSet);
                }
            }
        }
    }

    public boolean damagedByEnoughPirates(UUID sourceId, Game game) {
        return damageSourceIds.keySet().contains(sourceId) && damageSourceIds.get(sourceId).size() > 2;
    }

    @Override
    public void reset() {
        super.reset();
        damageSourceIds.clear();
    }
}

class ControllerDealtDamageByPiratesPredicate implements Predicate<Permanent> {

    public ControllerDealtDamageByPiratesPredicate() {
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        DamagedByPiratesWatcher watcher = (DamagedByPiratesWatcher) game.getState().getWatchers().get(DamagedByPiratesWatcher.class.getSimpleName());
        if (watcher != null) {
            return watcher.damagedByEnoughPirates(input.getControllerId(), game);
        }
        return false;
    }
}
