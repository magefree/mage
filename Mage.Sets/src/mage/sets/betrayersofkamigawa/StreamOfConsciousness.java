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
package mage.sets.betrayersofkamigawa;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class StreamOfConsciousness extends CardImpl<StreamOfConsciousness> {

    public StreamOfConsciousness(UUID ownerId) {
        super(ownerId, 53, "Stream of Consciousness", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");
        this.color.setBlue(true);

        // Target player shuffles up to four target cards from his or her graveyard into his or her library.
        this.getSpellAbility().addEffect(new StreamOfConsciousnessEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new StreamOfConsciousnessTarget());

    }

    public StreamOfConsciousness(final StreamOfConsciousness card) {
        super(card);
    }

    @Override
    public StreamOfConsciousness copy() {
        return new StreamOfConsciousness(this);
    }
}

class StreamOfConsciousnessEffect extends OneShotEffect<StreamOfConsciousnessEffect> {

    public StreamOfConsciousnessEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to four target cards from his or her graveyard into his or her library";
    }

    public StreamOfConsciousnessEffect(final StreamOfConsciousnessEffect effect) {
        super(effect);
    }

    @Override
    public StreamOfConsciousnessEffect copy() {
        return new StreamOfConsciousnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            List<UUID> targets = source.getTargets().get(1).getTargets();
            boolean shuffle = false;
            for (UUID targetId : targets) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    if (player.getGraveyard().contains(card.getId())) {
                        player.getGraveyard().remove(card);
                        card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                        shuffle = true;
                    }
                }
            }
            if (shuffle) {
                player.getLibrary().shuffle();
            }
            return true;
        }
        return false;
    }
}

class StreamOfConsciousnessTarget extends TargetCard<StreamOfConsciousnessTarget> {

    public StreamOfConsciousnessTarget() {
        super(0, 4, Zone.GRAVEYARD, new FilterCard("cards from target player's graveyard"));
    }

    public StreamOfConsciousnessTarget(final StreamOfConsciousnessTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            UUID firstTarget = source.getFirstTarget();
            if (firstTarget != null && game.getPlayer(firstTarget).getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public StreamOfConsciousnessTarget copy() {
        return new StreamOfConsciousnessTarget(this);
    }
}
