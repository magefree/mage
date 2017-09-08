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
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PirateToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public class VraskaRelicSeeker extends CardImpl {

    public VraskaRelicSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(6));

        //+2: Create a 2/2 black Pirate creature token with menace.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new PirateToken()), 2));

        //-3: Destroy target artifact, creature, or enchantment. Create a colorless Treasure artifact token with "T, Sacrfice this artifact. Add one mana of any color to your mana pool."
        Ability ability = new LoyaltyAbility(new VraskaRelicSeekerDestroyEffect(), -3);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT));
        this.addAbility(ability);

        //-10: Target player's life total becomes 1
        ability = new LoyaltyAbility(new VraskaRelicSeekerLifeTotalEffect(), -10);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public VraskaRelicSeeker(final VraskaRelicSeeker card) {
        super(card);
    }

    @Override
    public VraskaRelicSeeker copy() {
        return new VraskaRelicSeeker(this);
    }
}

class VraskaRelicSeekerDestroyEffect extends OneShotEffect {

    VraskaRelicSeekerDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target artifact, creature, or enchantment. Create a colorless Treasure artifact token with \"{T}, Sacrfice this artifact. Add one mana of any color to your mana pool.\"";
    }

    VraskaRelicSeekerDestroyEffect(final VraskaRelicSeekerDestroyEffect effect) {
        super(effect);
    }

    @Override
    public VraskaRelicSeekerDestroyEffect copy() {
        return new VraskaRelicSeekerDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return new CreateTokenEffect(new TreasureToken()).apply(game, source);
    }
}

class VraskaRelicSeekerLifeTotalEffect extends OneShotEffect {

    public VraskaRelicSeekerLifeTotalEffect() {
        super(Outcome.Benefit);
        staticText = "Target player's life total becomes 1";
    }

    public VraskaRelicSeekerLifeTotalEffect(VraskaRelicSeekerLifeTotalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.setLife(1, game);
            return true;
        }
        return false;
    }

    @Override
    public VraskaRelicSeekerLifeTotalEffect copy() {
        return new VraskaRelicSeekerLifeTotalEffect(this);
    }
}
