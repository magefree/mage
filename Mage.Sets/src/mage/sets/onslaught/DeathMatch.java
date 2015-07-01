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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author LoneFox

 */
public class DeathMatch extends CardImpl {

    private final UUID originalId;

    public DeathMatch(UUID ownerId) {
        super(ownerId, 136, "Death Match", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "ONS";

        // Whenever a creature enters the battlefield, that creature's controller may have target creature of his or her choice get -3/-3 until end of turn.
        // NOTE: The ability being optional is implemented in the subclass to give the choice to correct player.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DeathMatchEffect(),
            new FilterCreaturePermanent(), false, SetTargetPointer.PLAYER, "");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        originalId = ability.getOriginalId();
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability.getOriginalId().equals(originalId)) {
            UUID controllerId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
            if(controllerId != null) {
                ability.getTargets().get(0).setTargetController(controllerId);
                ability.getEffects().get(0).setTargetPointer(new FirstTargetPointer());
            }
        }
    }

    public DeathMatch(final DeathMatch card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public DeathMatch copy() {
        return new DeathMatch(this);
    }
}

class DeathMatchEffect extends OneShotEffect {

    public DeathMatchEffect() {
        super(Outcome.UnboostCreature);
        staticText="that creature's controller may have target creature of his or her choice get -3/-3 until end of turn.";
    }

    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getTargetController());
        if(player != null) {
            if(player.chooseUse(outcome, "Give targeted creature -3/-3 ?", source, game)) {
                game.addEffect(new BoostTargetEffect(-3, -3, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }

    public DeathMatchEffect(final DeathMatchEffect effect) {
        super(effect);
    }

    public DeathMatchEffect copy() {
        return new DeathMatchEffect(this);
    }
}
