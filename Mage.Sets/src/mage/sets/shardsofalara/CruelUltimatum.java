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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public class CruelUltimatum extends CardImpl {

    public CruelUltimatum(UUID ownerId) {
        super(ownerId, 164, "Cruel Ultimatum", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{U}{U}{B}{B}{B}{R}{R}");
        this.expansionSetCode = "ALA";

        // Target opponent sacrifices a creature, discards three cards, then loses 5 life.
        // You return a creature card from your graveyard to your hand, draw three cards, then gain 5 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SacrificeEffect(new FilterCreaturePermanent(), 1, "Target opponent"));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(3));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(5));

        this.getSpellAbility().addEffect(new CruelUltimatumEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(5));
    }

    public CruelUltimatum(final CruelUltimatum card) {
        super(card);
    }

    @Override
    public CruelUltimatum copy() {
        return new CruelUltimatum(this);
    }
}

class CruelUltimatumEffect extends OneShotEffect {

    public CruelUltimatumEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return a creature card from your graveyard to your hand";
    }

    public CruelUltimatumEffect(final CruelUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public CruelUltimatumEffect copy() {
        return new CruelUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"));
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game) && controller.choose(Outcome.ReturnToHand, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card == null) {
                return false;
            }
            controller.moveCards(card, null, Zone.HAND, source, game);
        }
        return true;
    }
}
