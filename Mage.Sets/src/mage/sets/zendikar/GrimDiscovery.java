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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public class GrimDiscovery extends CardImpl<GrimDiscovery> {

    private static final FilterCreatureCard filterCreatureCard = new FilterCreatureCard("creature card from your graveyard");
    private static final FilterLandCard filterLandCard = new FilterLandCard("land card from your graveyard");

    public GrimDiscovery(UUID ownerId) {
        super(ownerId, 91, "Grim Discovery", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "ZEN";

        this.color.setBlack(true);

        // Choose one or both - Return target creature card from your graveyard to your hand; and/or return target land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filterCreatureCard));

        Mode mode1 = new Mode();
        mode1.getEffects().add(new ReturnToHandTargetEffect());
        mode1.getTargets().add(new TargetCardInYourGraveyard(filterLandCard));
        this.getSpellAbility().addMode(mode1);

        Mode mode2 = new Mode();
        mode2.getEffects().add(new GrimDiscoveryEffect());
        mode2.getTargets().add(new TargetCardInYourGraveyard(filterCreatureCard));
        mode2.getTargets().add(new TargetCardInYourGraveyard(filterLandCard));
        this.getSpellAbility().addMode(mode2);
    }

    public GrimDiscovery(final GrimDiscovery card) {
        super(card);
    }

    @Override
    public GrimDiscovery copy() {
        return new GrimDiscovery(this);
    }
}

class GrimDiscoveryEffect extends OneShotEffect<GrimDiscoveryEffect> {

    public GrimDiscoveryEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature card from your graveyard and target land card from your graveyard to your hand";
    }

    public GrimDiscoveryEffect(final GrimDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public GrimDiscoveryEffect copy() {
        return new GrimDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            result |= card.moveToZone(Zone.HAND, source.getId(), game, true);
        }
        card = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (card != null) {
            result |= card.moveToZone(Zone.HAND, source.getId(), game, true);
        }
        return result;
    }
}
