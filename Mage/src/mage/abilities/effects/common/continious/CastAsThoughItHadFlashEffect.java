/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.abilities.effects.common.continious;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class CastAsThoughItHadFlashEffect extends AsThoughEffectImpl<CastAsThoughItHadFlashEffect> {

    private final FilterCard filter;
    private final boolean anyPlayer;

    public CastAsThoughItHadFlashEffect(Duration duration, FilterCard filter) {
        this(duration, filter, false);
    }

    public CastAsThoughItHadFlashEffect(Duration duration, FilterCard filter, boolean anyPlayer) {
        super(AsThoughEffectType.CAST, duration, Outcome.Benefit);
        this.filter = filter;
        this.anyPlayer = anyPlayer;
        staticText = setText();
    }

    public CastAsThoughItHadFlashEffect(final CastAsThoughItHadFlashEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.anyPlayer = effect.anyPlayer;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastAsThoughItHadFlashEffect copy() {
        return new CastAsThoughItHadFlashEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && filter.match(card, game) && card.getSpellAbility().isInUseableZone(game, card, false)) {
            if (anyPlayer) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    return controller.getInRange().contains(card.getOwnerId());
                }
            } else {
                return card.getOwnerId().equals(source.getControllerId());
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        if (anyPlayer) {
            sb.append("Any player");
        } else {
            sb.append("You");
        }
        sb.append(" may cast ");
        sb.append(filter.getMessage());
        if (!duration.toString().isEmpty()) {
            if (duration.equals(Duration.EndOfTurn)) {
                sb.append(" this turn");
            } else {
                sb.append(" ");
                sb.append(duration.toString());
            }
        }
        return sb.append(" as though they had flash").toString();
    }
}
