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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class RideDown extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blocking creature");

    static {
        filter.add(new BlockingPredicate());
    }

    public RideDown(UUID ownerId) {
        super(ownerId, 194, "Ride Down", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{R}{W}");
        this.expansionSetCode = "KTK";

        this.color.setRed(true);
        this.color.setWhite(true);

        // Destroy target blocking creature. Creatures that were blocked by that creature this combat gain trample until end of turn.
        this.getSpellAbility().addEffect(new RideDownEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

    }

    public RideDown(final RideDown card) {
        super(card);
    }

    @Override
    public RideDown copy() {
        return new RideDown(this);
    }
}

class RideDownEffect extends OneShotEffect {

    public RideDownEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target blocking creature. Creatures that were blocked by that creature this combat gain trample until end of turn";
    }

    public RideDownEffect(final RideDownEffect effect) {
        super(effect);
    }

    @Override
    public RideDownEffect copy() {
        return new RideDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (blockingCreature != null) {
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getBlockers().contains(blockingCreature.getId())) {
                        for (UUID attackerId: combatGroup.getAttackers()) {
                            ContinuousEffect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                            effect.setTargetPointer(new FixedTarget(attackerId));
                            game.addEffect(effect, source);
                        }
                        break;
                    }
                }
                blockingCreature.destroy(source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}
