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
package mage.sets.mirage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class EbonyCharm extends CardImpl {

    public EbonyCharm(UUID ownerId) {
        super(ownerId, 18, "Ebony Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "MIR";

        // Choose one - Target opponent loses 1 life and you gain 1 life;
        this.getSpellAbility().addEffect(new EbonyCharmDrainEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        
        // or exile up to three target cards from a single graveyard; 
        Mode mode = new Mode();
        mode.getEffects().add(new EbonyCharmExileEffect());
        mode.getTargets().add((new TargetCardInASingleGraveyard(0, 3, new FilterCard("up to three target cards from a single graveyard"))));
        this.getSpellAbility().addMode(mode);
        
        // or target creature gains fear until end of turn.
        mode = new Mode();
        mode.getTargets().add(new TargetCreaturePermanent());
        mode.getEffects().add(new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addMode(mode);
    }

    public EbonyCharm(final EbonyCharm card) {
        super(card);
    }

    @Override
    public EbonyCharm copy() {
        return new EbonyCharm(this);
    }
}

class EbonyCharmDrainEffect extends OneShotEffect {

    EbonyCharmDrainEffect() {
        super(Outcome.Damage);
        staticText = "target opponent loses 1 life and you gain 1 life";
    }

    EbonyCharmDrainEffect(final EbonyCharmDrainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controllerPlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controllerPlayer != null) {
            targetPlayer.damage(1, source.getSourceId(), game, false, true);
            controllerPlayer.gainLife(1, game);
        }
        return false;
    }

    @Override
    public EbonyCharmDrainEffect copy() {
        return new EbonyCharmDrainEffect(this);
    }

}

class EbonyCharmExileEffect extends OneShotEffect {

    public EbonyCharmExileEffect() {
            super(Outcome.Exile);
            this.staticText = "Exile up to three target cards from a single graveyard";
    }

    public EbonyCharmExileEffect(final EbonyCharmExileEffect effect) {
            super(effect);
    }

    @Override
    public EbonyCharmExileEffect copy() {
            return new EbonyCharmExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetID : source.getTargets().get(0).getTargets()) {
            Card card = game.getCard(targetID);
            if (card != null) {
                card.moveToExile(null, "", source.getSourceId(), game);
            }
        }
        return true;
    }
}
