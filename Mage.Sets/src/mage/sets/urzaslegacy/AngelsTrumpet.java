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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author fireshoes
 */
public class AngelsTrumpet extends CardImpl {

    public AngelsTrumpet(UUID ownerId) {
        super(ownerId, 121, "Angel's Trumpet", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "ULG";

        // All creatures have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));
        
        // At the beginning of each player's end step, tap all untapped creatures that player controls that didn't attack this turn. Angel's Trumpet deals damage to the player equal to the number of creatures tapped this way.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AngelsTrumpetTapEffect(), TargetController.ANY, false), new AttackedThisTurnWatcher());
    }

    public AngelsTrumpet(final AngelsTrumpet card) {
        super(card);
    }

    @Override
    public AngelsTrumpet copy() {
        return new AngelsTrumpet(this);
    }
}

class AngelsTrumpetTapEffect extends OneShotEffect {
    
    AngelsTrumpetTapEffect() {
        super(Outcome.Tap);
        this.staticText = "tap all untapped creatures that player controls that didn't attack this turn. Angel's Trumpet deals damage to the player equal to the number of creatures tapped this way";
    }
    
    AngelsTrumpetTapEffect(final AngelsTrumpetTapEffect effect) {
        super(effect);
    }
    
    @Override
    public AngelsTrumpetTapEffect copy() {
        return new AngelsTrumpetTapEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        int count = 0;
        if (player != null) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                // Untapped creatures are safe.
                if (creature.isTapped()) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get("AttackedThisTurn");
                if (watcher != null && watcher.getAttackedThisTurnCreatures().contains(creature.getId())) {
                    continue;
                }
                // Tap the rest.
                creature.tap(game);
                count++;
            }
            if (count > 0) {
                player.damage(count, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}