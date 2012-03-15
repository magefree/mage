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
package mage.sets.darkascension;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class GruesomeDiscovery extends CardImpl<GruesomeDiscovery> {

    public GruesomeDiscovery(UUID ownerId) {
        super(ownerId, 66, "Gruesome Discovery", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "DKA";

        this.color.setBlack(true);

        // Target player discards two cards.
        // Morbid - If a creature died this turn, instead that player reveals his or her hand, you choose two cards from it, then that player discards those cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GruesomeDiscoveryEffect(),
                new DiscardTargetEffect(2),
                MorbidCondition.getInstance(),
                "Target player discards two cards. Morbid - If a creature died this turn, instead that player reveals his or her hand, you choose two cards from it, then that player discards those cards"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public GruesomeDiscovery(final GruesomeDiscovery card) {
        super(card);
    }

    @Override
    public GruesomeDiscovery copy() {
        return new GruesomeDiscovery(this);
    }
}

class GruesomeDiscoveryEffect extends OneShotEffect<GruesomeDiscoveryEffect> {

    public GruesomeDiscoveryEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals his or her hand, you choose two cards from it, then that player discards those cards";
    }

    public GruesomeDiscoveryEffect(final GruesomeDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public GruesomeDiscoveryEffect copy() {
        return new GruesomeDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());

        if (player != null && targetPlayer != null) {
            targetPlayer.revealCards("Gruesome Discovery", targetPlayer.getHand(), game);

            if (targetPlayer.getHand().size() <= 2) {
                targetPlayer.discard(2, source, game);
            }

            TargetCard target = new TargetCard(2, Zone.PICK, new FilterCard());
            target.setRequired(true);
            if (player.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = targetPlayer.getHand().get(targetId, game);
                    if (card != null) {
                        return targetPlayer.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
