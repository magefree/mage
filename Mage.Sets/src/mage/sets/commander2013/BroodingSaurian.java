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
package mage.sets.commander2013;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class BroodingSaurian extends CardImpl<BroodingSaurian> {

    public BroodingSaurian(UUID ownerId) {
        super(ownerId, 138, "Brooding Saurian", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "C13";
        this.subtype.add("Lizard");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of each end step, each player gains control of all nontoken permanents he or she owns.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new BroodingSaurianControlEffect(), TargetController.ANY, false));
    }

    public BroodingSaurian(final BroodingSaurian card) {
        super(card);
    }

    @Override
    public BroodingSaurian copy() {
        return new BroodingSaurian(this);
    }
}

class BroodingSaurianControlEffect extends ContinuousEffectImpl<BroodingSaurianControlEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public BroodingSaurianControlEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "each player gains control of all nontoken permanents he or she owns";
    }

    public BroodingSaurianControlEffect(final BroodingSaurianControlEffect effect) {
        super(effect);
    }

    @Override
    public BroodingSaurianControlEffect copy() {
        return new BroodingSaurianControlEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // add all creatures in range
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                FilterPermanent playerFilter = filter.copy();
                playerFilter.add(new OwnerIdPredicate(playerId));
                for (Permanent permanent :game.getBattlefield().getActivePermanents(playerFilter, playerId, game)) {
                    objects.add(permanent.getId());
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toRemove = new HashSet<UUID>();
        for (UUID creatureId :objects) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null) {
                if (!creature.getControllerId().equals(creature.getOwnerId())) {
                    creature.changeControllerId(creature.getOwnerId(), game);
                }
            } else {
                toRemove.add(creatureId);
            }
        }
        objects.removeAll(toRemove);
        if (objects.isEmpty()) {
            this.discard();
        }
        return true;
    }
}
