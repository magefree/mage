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
package mage.sets.alarareborn;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class Lavalanche extends CardImpl<Lavalanche> {

    public Lavalanche(UUID ownerId) {
        super(ownerId, 118, "Lavalanche", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{B}{R}{G}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);

        // Lavalanche deals X damage to target player and each creature he or she controls.
        this.getSpellAbility().addEffect(new LavalancheEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
    }

    public Lavalanche(final Lavalanche card) {
        super(card);
    }

    @Override
    public Lavalanche copy() {
        return new Lavalanche(this);
    }
}

class LavalancheEffect extends OneShotEffect<LavalancheEffect> {

    private DynamicValue amount;

    public LavalancheEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "Lavalanche deals X damage to target player and each creature he or she controls";
    }

    public LavalancheEffect(final LavalancheEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public LavalancheEffect copy() {
        return new LavalancheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }
        targetPlayer.damage(amount.calculate(game, source), source.getId(), game, false, true);
        FilterPermanent filter = new FilterPermanent("and each creature he or she controls");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerIdPredicate(targetPlayer.getId()));
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getId(), game);
        for (Permanent permanent: permanents) {
            permanent.damage(amount.calculate(game, source), source.getSourceId(), game, true, false);
        }
        return true;
    }
}
