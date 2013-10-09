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
package mage.sets.nemesis;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class TangleWire extends CardImpl<TangleWire> {

    public TangleWire(UUID ownerId) {
        super(ownerId, 139, "Tangle Wire", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "NMS";

        // Fading 4
        this.addAbility(new FadingAbility(4, this));
        // At the beginning of each player's upkeep, that player taps an untapped artifact, creature, or land he or she controls for each fade counter on Tangle Wire.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new TangleWireEffect(), TargetController.ANY, false, true));
    }

    public TangleWire(final TangleWire card) {
        super(card);
    }

    @Override
    public TangleWire copy() {
        return new TangleWire(this);
    }
}
class TangleWireEffect extends OneShotEffect<TangleWireEffect> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifact, creature, or land he or she controls");
    static{
        filter.add(Predicates.not(new TappedPredicate()));
    }
    
    TangleWireEffect() {
        super(Outcome.Sacrifice);
        staticText = "that player taps an untapped artifact, creature, or land he or she controls for each fade counter on Tangle Wire";
    }

    TangleWireEffect(final TangleWireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        
        int targetCount = game.getBattlefield().countAll(filter, player.getId(), game);
        int counterCount = permanent.getCounters().getCount(CounterType.FADE);
        int amount = Math.min(counterCount, targetCount);
        
        Target target = new TargetControlledPermanent(amount, amount, filter, false);
        target.setRequired(true);
        target.setNotTarget(true);

        if (player.chooseTarget(Outcome.Tap, target, source, game)) {
            boolean abilityApplied = false;

            for (UUID uuid : target.getTargets()) {
                Permanent selectedPermanent = game.getPermanent(uuid);
                if ( selectedPermanent != null ) {
                    abilityApplied |= selectedPermanent.tap(game);
                }
            }

            return abilityApplied;
        }
        return false;
    }

    @Override
    public TangleWireEffect copy() {
        return new TangleWireEffect(this);
    }
}