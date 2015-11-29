/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExileTargetEffect extends OneShotEffect {

    private Zone onlyFromZone;
    private String exileZone = null;
    private UUID exileId = null;

    public ExileTargetEffect(String effectText) {
        this();
        this.staticText = effectText;
    }

    public ExileTargetEffect() {
        this(null, "");
    }

    public ExileTargetEffect(UUID exileId, String exileZone) {
        this(exileId, exileZone, null);
    }

    public ExileTargetEffect(UUID exileId, String exileZone, Zone onlyFromZone) {
        super(Outcome.Exile);
        this.exileZone = exileZone;
        this.exileId = exileId;
        this.onlyFromZone = onlyFromZone;
    }

    public ExileTargetEffect(final ExileTargetEffect effect) {
        super(effect);
        this.exileZone = effect.exileZone;
        this.exileId = effect.exileId;
        this.onlyFromZone = effect.onlyFromZone;
    }

    @Override
    public ExileTargetEffect copy() {
        return new ExileTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> toExile = new LinkedHashSet<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    Zone currentZone = game.getState().getZone(permanent.getId());
                    if (!currentZone.equals(Zone.EXILED) && (onlyFromZone == null || onlyFromZone.equals(Zone.BATTLEFIELD))) {
                        toExile.add(permanent);
                    }
                } else {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        Zone currentZone = game.getState().getZone(card.getId());
                        if (!currentZone.equals(Zone.EXILED) && (onlyFromZone == null || onlyFromZone.equals(currentZone))) {
                            toExile.add(card);
                        }
                    }
                }
            }
            controller.moveCardsToExile(toExile, source, game, true, exileId, exileZone);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().isEmpty()) {
            return "exile it";
        } else {
            return "exile target " + mode.getTargets().get(0).getTargetName();
        }
    }
}
