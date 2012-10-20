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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class DestroyTheEvidence extends CardImpl<DestroyTheEvidence> {

    public DestroyTheEvidence(UUID ownerId) {
        super(ownerId, 64, "Destroy the Evidence", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{B}");
        this.expansionSetCode = "RTR";

        this.color.setBlack(true);

        // Destroy target land. Its controller reveals cards from the top of his
        // or her library until he or she reveals a land card, then puts those cards into his or her graveyard.
        TargetLandPermanent target = new TargetLandPermanent();
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DestroyTheEvidenceEffect());
    }

    public DestroyTheEvidence(final DestroyTheEvidence card) {
        super(card);
    }

    @Override
    public DestroyTheEvidence copy() {
        return new DestroyTheEvidence(this);
    }
}

class DestroyTheEvidenceEffect extends OneShotEffect<DestroyTheEvidenceEffect> {

    public DestroyTheEvidenceEffect() {
        super(Constants.Outcome.Discard);
        this.staticText = "Its controller reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard";
    }

    public DestroyTheEvidenceEffect(final DestroyTheEvidenceEffect effect) {
        super(effect);
    }

    @Override
    public DestroyTheEvidenceEffect copy() {
        return new DestroyTheEvidenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID landTarget = source.getFirstTarget();
        Permanent landPermanent = (Permanent) game.getLastKnownInformation(landTarget, Zone.BATTLEFIELD);
        Card landCard = game.getCard(landTarget);
        if (landPermanent != null && landCard != null) {
            Player player = game.getPlayer(landPermanent.getControllerId());
            if (player == null) {
                return false;
            }

            boolean landFound = false;
            while (player.getLibrary().size() > 0 && !landFound) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false);
                    if (card.getCardType().contains(CardType.LAND)) {
                        landFound = true;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
