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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.DrawCardEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public class CruelUltimatum extends CardImpl<CruelUltimatum> {

    public CruelUltimatum(UUID ownerId) {
        super(ownerId, 164, "Cruel Ultimatum", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{U}{U}{B}{B}{B}{R}{R}");
        this.expansionSetCode = "ALA";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);

        // Target opponent sacrifices a creature, discards three cards, then loses 5 life. You return a creature card from your graveyard to your hand, draw three cards, then gain 5 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SacrificeEffect(FilterCreaturePermanent.getDefault(), 1, "a creature"));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(3));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(5));

        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.getSpellAbility().addEffect(new CruelUltimatumEffect());
        this.getSpellAbility().addEffect(new DrawCardEffect(3));
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

class CruelUltimatumEffect extends OneShotEffect<CruelUltimatumEffect> {

    public CruelUltimatumEffect() {
        super(Outcome.ReturnToHand);
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
        Card card = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (card != null) {
            return card.moveToZone(Zone.HAND, source.getId(), game, true);
        }
        return false;
    }
}
