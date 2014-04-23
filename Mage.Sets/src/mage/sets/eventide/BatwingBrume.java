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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class BatwingBrume extends CardImpl<BatwingBrume> {

    public BatwingBrume(UUID ownerId) {
        super(ownerId, 81, "Batwing Brume", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{W/B}");
        this.expansionSetCode = "EVE";

        this.color.setBlack(true);
        this.color.setWhite(true);

        // Prevent all combat damage that would be dealt this turn if {W} was spent to cast Batwing Brume. Each player loses 1 life for each attacking creature he or she controls if {B} was spent to cast Batwing Brume.
        this.getSpellAbility().addEffect(new ConditionalContinousEffect(
                new PreventAllDamageByAllEffect(Duration.EndOfTurn, true),
                new ManaWasSpentCondition(ColoredManaSymbol.W), "Prevent all combat damage that would be dealt this turn if {W} was spent to cast {this}", true));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new BatwingBrumeEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.W), "Each player loses 1 life for each attacking creature he or she controls if {B} was spent to cast {this}"));
        this.addInfo("Info1", "<i>(Do both if {W}{B} was spent.)<i>");

    }

    public BatwingBrume(final BatwingBrume card) {
        super(card);
    }

    @Override
    public BatwingBrume copy() {
        return new BatwingBrume(this);
    }
}

class BatwingBrumeEffect extends OneShotEffect<BatwingBrumeEffect> {

    public BatwingBrumeEffect() {
        super(Outcome.LoseLife);
    }

    public BatwingBrumeEffect(final BatwingBrumeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                final int amount = game.getBattlefield().getAllActivePermanents(new FilterAttackingCreature(), player.getId(), game).size();
                player.loseLife(amount, game);
            }
        }
        return true;
    }

    @Override
    public BatwingBrumeEffect copy() {
        return new BatwingBrumeEffect(this);
    }
}