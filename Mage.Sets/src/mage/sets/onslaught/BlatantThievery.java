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
import mage.abilities.SpellAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class BlatantThievery extends CardImpl {

    public BlatantThievery(UUID ownerId) {
        super(ownerId, 71, "Blatant Thievery", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}{U}{U}");
        this.expansionSetCode = "ONS";

        // For each opponent, gain control of target permanent that player controls.
        this.getSpellAbility().addEffect(new BlatantThieveryEffect());
    }

    public BlatantThievery(final BlatantThievery card) {
        super(card);
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterPermanent filter = new FilterPermanent("permanent controlled by " + opponent.getName());
                    filter.add(new ControllerIdPredicate(opponentId));
                    ability.addTarget(new TargetPermanent(filter));
                }
            }
        }
    }

    @Override
    public BlatantThievery copy() {
        return new BlatantThievery(this);
    }
}

class BlatantThieveryEffect extends OneShotEffect {
    
    BlatantThieveryEffect() {
        super(Outcome.GainControl);
        this.staticText = "For each opponent, gain control of target permanent that player controls";
    }
    
    BlatantThieveryEffect(final BlatantThieveryEffect effect) {
        super(effect);
    }
    
    @Override
    public BlatantThieveryEffect copy() {
        return new BlatantThieveryEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target : source.getTargets()) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
            game.addEffect(effect, source);
        }
        return true;
    }
}
