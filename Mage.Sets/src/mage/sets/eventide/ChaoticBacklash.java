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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class ChaoticBacklash extends CardImpl<ChaoticBacklash> {

    public ChaoticBacklash(UUID ownerId) {
        super(ownerId, 49, "Chaotic Backlash", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{R}");
        this.expansionSetCode = "EVE";

        this.color.setRed(true);

        // Chaotic Backlash deals damage to target player equal to twice the number of white and/or blue permanents he or she controls.
        this.getSpellAbility().addEffect(new ChaoticBacklashEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));
        
    }

    public ChaoticBacklash(final ChaoticBacklash card) {
        super(card);
    }

    @Override
    public ChaoticBacklash copy() {
        return new ChaoticBacklash(this);
    }
}

class ChaoticBacklashEffect extends OneShotEffect<ChaoticBacklashEffect> {
    
    private static final FilterPermanent filter = new FilterPermanent("white and/or blue permanents he or she controls");
    
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)));
    }

    public ChaoticBacklashEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals damage to target player equal to twice the number of white and/or blue permanents he or she controls";
    }

    public ChaoticBacklashEffect(final ChaoticBacklashEffect effect) {
        super(effect);
    }

    @Override
    public ChaoticBacklashEffect copy() {
        return new ChaoticBacklashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            int amount = 2 * game.getBattlefield().countAll(filter, targetPlayer.getId(), game);
            targetPlayer.damage(amount, source.getId(), game, false, true);
            return true;
        }
        return false;
    }
}