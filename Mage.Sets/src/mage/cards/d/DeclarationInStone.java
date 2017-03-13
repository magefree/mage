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
package mage.cards.d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.ClueArtifactToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class DeclarationInStone extends CardImpl {

    public DeclarationInStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target creature and all other creatures its controller controls with the same name as that creature.
        // That player investigates for each nontoken creature exiled this way.
        this.getSpellAbility().addEffect(new DeclarationInStoneEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public DeclarationInStone(final DeclarationInStone card) {
        super(card);
    }

    @Override
    public DeclarationInStone copy() {
        return new DeclarationInStone(this);
    }
}

class DeclarationInStoneEffect extends OneShotEffect {

    public DeclarationInStoneEffect() {
        super(Outcome.Exile);
        staticText = "Exile target creature and all other creatures its controller controls with the same name as that creature. That player investigates for each nontoken creature exiled this way.";
    }

    public DeclarationInStoneEffect(final DeclarationInStoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                Set<Card> cardsToExile = new HashSet<>();
                int nonTokenCount = 0;
                if (targetPermanent.getName().isEmpty()) { // face down creature
                    cardsToExile.add(targetPermanent);
                    if (!(targetPermanent instanceof PermanentToken)) {
                        nonTokenCount++;
                    }
                } else {
                    if (cardsToExile.add(targetPermanent)
                            && !(targetPermanent instanceof PermanentToken)) {
                        nonTokenCount++;
                    }
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), targetPermanent.getControllerId(), game)) {
                        if (!permanent.getId().equals(targetPermanent.getId())
                                && permanent.getName().equals(targetPermanent.getName())) {
                            cardsToExile.add(permanent);
                            // exiled count only matters for non-tokens
                            if (!(permanent instanceof PermanentToken)) {
                                nonTokenCount++;
                            }
                        }
                    }
                }
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                game.applyEffects();
                if (nonTokenCount > 0) {
                    new ClueArtifactToken().putOntoBattlefield(nonTokenCount, game, source.getSourceId(), targetPermanent.getControllerId(), false, false);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public DeclarationInStoneEffect copy() {
        return new DeclarationInStoneEffect(this);
    }
}
