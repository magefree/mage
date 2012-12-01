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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DevouringGreed extends CardImpl<DevouringGreed> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("any number of Spirits");

    static {
        filter.add(new SubtypePredicate("Spirit"));
    }

    public DevouringGreed(UUID ownerId) {
        super(ownerId, 110, "Devouring Greed", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");
        this.color.setBlack(true);

        // As an additional cost to cast Devouring Greed, you may sacrifice any number of Spirits.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true, false)));

        // Target player loses 2 life plus 2 life for each Spirit sacrificed this way. You gain that much life.
        this.getSpellAbility().addEffect(new DevouringGreedEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));

    }

    public DevouringGreed(final DevouringGreed card) {
        super(card);
    }

    @Override
    public DevouringGreed copy() {
        return new DevouringGreed(this);
    }
}

class DevouringGreedEffect extends OneShotEffect<DevouringGreedEffect> {

    public DevouringGreedEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Target player loses 2 life plus 2 life for each Spirit sacrificed this way. You gain that much life";
    }

    public DevouringGreedEffect(final DevouringGreedEffect effect) {
        super(effect);
    }

    @Override
    public DevouringGreedEffect copy() {
        return new DevouringGreedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberSpirits = 0;
        for (Cost cost :source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                numberSpirits += ((SacrificeTargetCost) cost).getPermanents().size();
            }
        }
        int amount = 2 + (numberSpirits * 2);
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && sourcePlayer != null) {
            targetPlayer.loseLife(amount, game);
            sourcePlayer.gainLife(amount, game);
            return true;
        }
        return false;
    }
}
