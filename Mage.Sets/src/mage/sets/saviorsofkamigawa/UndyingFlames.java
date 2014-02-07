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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EpicEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 *
 */
public class UndyingFlames extends CardImpl<UndyingFlames> {

    public UndyingFlames(UUID ownerId) {
        super(ownerId, 119, "Undying Flames", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "SOK";

        this.color.setRed(true);

        // Exile cards from the top of your library until you exile a nonland card. Undying Flames deals damage to target creature or player equal to that card's converted mana cost.
        this.getSpellAbility().addEffect(new UndyingFlamesEffect());

        // Epic
        this.getSpellAbility().addEffect(new EpicEffect());

    }

    public UndyingFlames(final UndyingFlames card) {
        super(card);
    }

    @Override
    public UndyingFlames copy() {
        return new UndyingFlames(this);
    }
}

class UndyingFlamesEffect extends OneShotEffect<UndyingFlamesEffect> {

    public UndyingFlamesEffect() {
        super(Outcome.Benefit);
        staticText = "Exile cards from the top of your library until you exile a nonland card. Undying Flames deals damage to target creature or player equal to that card's converted mana cost";
    }

    public UndyingFlamesEffect(final UndyingFlamesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        boolean applied = false;
        Player you = game.getPlayer(source.getControllerId());
        while (you != null
                && you.getLibrary().size() > 0) {
            Card card = you.getLibrary().removeFromTop(game);
            if (card != null) {
                if (card.getCardType().contains(CardType.LAND)) {
                    card.moveToExile(exileId, "Undying Flames", source.getSourceId(), game);
                } else {
                    Target target = new TargetCreatureOrPlayer();
                    if (you.chooseTarget(Outcome.Damage, target, source, game)) {
                        Permanent creature = game.getPermanent(target.getFirstTarget());
                        if (creature != null) {
                            creature.damage(card.getManaCost().convertedManaCost(), source.getSourceId(), game, true, false);
                            applied = true;
                            break;
                        }
                        Player player = game.getPlayer(target.getFirstTarget());
                        if (player != null) {
                            player.damage(card.getManaCost().convertedManaCost(), source.getSourceId(), game, true, false);
                            applied = true;
                            break;
                        }
                    }
                }
            }
        }
        return applied;
    }

    @Override
    public UndyingFlamesEffect copy() {
        return new UndyingFlamesEffect(this);
    }
}