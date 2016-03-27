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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ReturnToBattlefieldUnderOwnerControlTargetEffect extends OneShotEffect {

    private boolean tapped;
    protected boolean fromExileZone;

    public ReturnToBattlefieldUnderOwnerControlTargetEffect() {
        this(false);
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped) {
        this(tapped, false);
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped, boolean fromExileZone) {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under its owner's control";
        this.tapped = tapped;
        this.fromExileZone = fromExileZone;
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(final ReturnToBattlefieldUnderOwnerControlTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.fromExileZone = effect.fromExileZone;
    }

    @Override
    public ReturnToBattlefieldUnderOwnerControlTargetEffect copy() {
        return new ReturnToBattlefieldUnderOwnerControlTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = null;
            if (fromExileZone) {
                UUID exilZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (exilZoneId != null) {
                    ExileZone exileZone = game.getExile().getExileZone(exilZoneId);
                    if (exileZone != null && getTargetPointer().getFirst(game, source) != null) {
                        card = exileZone.get(getTargetPointer().getFirst(game, source), game);
                    }
                }
            } else {
                card = game.getCard(getTargetPointer().getFirst(game, source));
            }
            if (card != null) {
                controller.moveCards(new CardsImpl(card).getCards(game),
                        Zone.BATTLEFIELD, source, game, tapped, false, true, null);
                return true;
            }
        }
        return false;
    }
}
