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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class MorgueBurst extends CardImpl<MorgueBurst> {

    public MorgueBurst(UUID ownerId) {
        super(ownerId, 86, "Morgue Burst", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{B}{R}");
        this.expansionSetCode = "DGM";

        this.color.setRed(true);
        this.color.setBlack(true);

        // Return target creature card from your graveyard to your hand. Morgue Burst deals damage to target creature or player equal to the power of the card returned this way.
        this.getSpellAbility().addEffect(new MorgueBurstEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public MorgueBurst(final MorgueBurst card) {
        super(card);
    }

    @Override
    public MorgueBurst copy() {
        return new MorgueBurst(this);
    }
}

class MorgueBurstEffect extends OneShotEffect<MorgueBurstEffect> {

    public MorgueBurstEffect() {
        super(Constants.Outcome.ReturnToHand);
    }

    public MorgueBurstEffect(final MorgueBurstEffect effect) {
        super(effect);
    }

    @Override
    public MorgueBurstEffect copy() {
        return new MorgueBurstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                card.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, false);
                int damage = card.getPower().getValue();
                Permanent creature = game.getPermanent(source.getTargets().get(1).getTargets().get(0));
                if (creature != null) {
                    creature.damage(damage, source.getSourceId(), game, true, false);
                    return true;
                }
                Player targetPlayer = game.getPlayer(source.getTargets().get(1).getTargets().get(0));
                if (targetPlayer != null) {
                    targetPlayer.damage(damage, source.getSourceId(), game, true, false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Return target creature card from your graveyard to your hand. Morgue Burst deals damage to target creature or player equal to the power of the card returned this way");
        return sb.toString();
    }
}
