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

package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import org.apache.log4j.spi.Filter;

/**
 *
 * @author North
 */
public class NaturesClaim extends CardImpl<NaturesClaim> {
    private static FilterPermanent filter = new FilterPermanent("artifact or enchantment");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.getCardType().add(CardType.ENCHANTMENT);
        filter.setScopeCardType(mage.filter.Filter.ComparisonScope.Any);
    }

    public NaturesClaim (UUID ownerId) {
        super(ownerId, 108, "Nature's Claim", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "WWK";
		this.color.setGreen(true);
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new NaturesClaimEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public NaturesClaim (final NaturesClaim card) {
        super(card);
    }

    @Override
    public NaturesClaim copy() {
        return new NaturesClaim(this);
    }
}

class NaturesClaimEffect extends OneShotEffect<NaturesClaimEffect> {
    NaturesClaimEffect() {
        super(Constants.Outcome.GainLife);
    }

    NaturesClaimEffect(final NaturesClaimEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(source), Constants.Zone.BATTLEFIELD);
        if (target != null) {
            Player player = game.getPlayer(target.getControllerId());
            if (player != null) {
                player.gainLife(4, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public NaturesClaimEffect copy() {
        return new NaturesClaimEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "Its controller gains 4 life";
    }
}