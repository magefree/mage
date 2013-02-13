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
package mage.sets.returntoravnica;

import java.util.ArrayList;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public class RakdosCharm extends CardImpl<RakdosCharm> {

    public RakdosCharm(UUID ownerId) {
        super(ownerId, 184, "Rakdos Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}{R}");
        this.expansionSetCode = "RTR";

        this.color.setBlack(true);
        this.color.setRed(true);

        // Choose one — Exile all cards from target player's graveyard;
        this.getSpellAbility().addEffect(new RakdosCharmExileEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // or destroy target artifact;
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);

        // or each creature deals 1 damage to its controller.
        mode = new Mode();
        mode.getEffects().add(new RakdosCharmDamageEffect());
        this.getSpellAbility().addMode(mode);
    }

    public RakdosCharm(final RakdosCharm card) {
        super(card);
    }

    @Override
    public RakdosCharm copy() {
        return new RakdosCharm(this);
    }


    private class RakdosCharmDamageEffect extends OneShotEffect<RakdosCharmDamageEffect> {

    public RakdosCharmDamageEffect() {
            super(Constants.Outcome.Detriment);
            staticText = "each creature deals 1 damage to its controller";
        }

        public RakdosCharmDamageEffect(final RakdosCharmDamageEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {

            FilterPermanent filterEnchantments = new FilterPermanent();
            filterEnchantments.add(new CardTypePredicate(CardType.CREATURE));

            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterEnchantments, source.getControllerId(), source.getSourceId(), game)) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    controller.damage(1, permanent.getId(), game, false, true);
                    game.informPlayers("1 damage to " + controller.getName() + " from " + permanent.getName());
                }
            }
            return true;
        }

        @Override
        public RakdosCharmDamageEffect copy() {
            return new RakdosCharmDamageEffect(this);
        }

    }

    class RakdosCharmExileEffect extends OneShotEffect<RakdosCharmExileEffect> {

        public RakdosCharmExileEffect() {
            super(Constants.Outcome.Exile);
            staticText = "Exile all cards from target player's graveyard";
        }

        @Override
        public RakdosCharmExileEffect copy() {
            return new RakdosCharmExileEffect();
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                ArrayList<UUID> graveyard = new ArrayList<UUID>(targetPlayer.getGraveyard());
                for (UUID cardId : graveyard) {
                    game.getCard(cardId).moveToZone(Zone.EXILED, cardId, game, false);
                }
                return true;
            }
            return false;
        }
    }
}