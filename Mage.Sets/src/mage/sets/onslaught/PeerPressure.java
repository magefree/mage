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
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class PeerPressure extends CardImpl {

    public PeerPressure(UUID ownerId) {
        super(ownerId, 101, "Peer Pressure", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "ONS";

        // Choose a creature type. If you control more creatures of that type than each other player, you gain control of all creatures of that type.
        this.getSpellAbility().addEffect(new PeerPressureEffect());
    }

    public PeerPressure(final PeerPressure card) {
        super(card);
    }

    @Override
    public PeerPressure copy() {
        return new PeerPressure(this);
    }
}

class PeerPressureEffect extends OneShotEffect {

    PeerPressureEffect() {
        super(Outcome.GainControl);
        this.staticText = "Choose a creature type. If you control more creatures of that type than each other player, you gain control of all creatures of that type";
    }

    PeerPressureEffect(final PeerPressureEffect effect) {
        super(effect);
    }

    @Override
    public PeerPressureEffect copy() {
        return new PeerPressureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose creature type");
            choice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!controller.choose(Outcome.GainControl, choice, game)) {
                if (!controller.isInGame()) {
                    return false;
                }
            }
            String chosenType = choice.getChoice();
            if (!game.isSimulation()) {
                game.informPlayers(controller.getLogName() + " has chosen " + chosenType);
            }
            UUID playerWithMost = null;
            int maxControlled = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                FilterPermanent filter = new FilterCreaturePermanent(chosenType, chosenType);
                filter.add(new ControllerIdPredicate(playerId));
                int controlled = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
                if (controlled > maxControlled) {
                    maxControlled = controlled;
                    playerWithMost = playerId;
                } else if (controlled == maxControlled) {
                    playerWithMost = null; // Do nothing in case of tie
                }
            }
            if (playerWithMost != null && playerWithMost.equals(controller.getId())) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(chosenType, chosenType), controller.getId(), source.getSourceId(), game)) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
