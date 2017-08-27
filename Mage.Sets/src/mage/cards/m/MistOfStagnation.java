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
package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public class MistOfStagnation extends CardImpl {

    public MistOfStagnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Permanents don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, new FilterPermanent("permanents"))));

        // At the beginning of each player's upkeep, that player chooses a permanent for each card in his or her graveyard, then untaps those permanents.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MistOfStagnationEffect(), TargetController.ANY, false));
    }

    public MistOfStagnation(final MistOfStagnation card) {
        super(card);
    }

    @Override
    public MistOfStagnation copy() {
        return new MistOfStagnation(this);
    }
}

class MistOfStagnationEffect extends OneShotEffect {

    MistOfStagnationEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player chooses a permanent for each card in his or her graveyard, then untaps those permanents";
    }

    MistOfStagnationEffect(final MistOfStagnationEffect effect) {
        super(effect);
    }

    @Override
    public MistOfStagnationEffect copy() {
        return new MistOfStagnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            int cardsInGrave = activePlayer.getGraveyard().size();
            if (cardsInGrave > 0) {
                Set<TargetPermanent> targets = new HashSet<>();
                for (int i = 1; 1 <= cardsInGrave; i++) {
                    TargetPermanent target = new TargetPermanent(1, 1, new FilterPermanent(), true);
                    target.setTargetController(activePlayer.getId());
                    target.setTargetController(activePlayer.getId());
                    if (target.canChoose(source.getSourceId(), activePlayer.getId(), game) && activePlayer.chooseTarget(Outcome.Untap, target, source, game)) {
                        targets.add(target);
                    }
                }
                for (TargetPermanent target : targets) {
                    Permanent p = game.getPermanent(target.getFirstTarget());
                    if (p != null) {
                        p.untap(game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
