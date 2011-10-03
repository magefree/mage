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
package mage.sets.innistrad;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class MemorysJourney extends CardImpl<MemorysJourney> {

    public MemorysJourney(UUID ownerId) {
        super(ownerId, 66, "Memory's Journey", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ISD";

        this.color.setBlue(true);

        // Target player shuffles up to three target cards from his or her graveyard into his or her library.
        this.getSpellAbility().addEffect(new MemorysJourneyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new MemorysJourneyTarget());
        // Flashback {G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{G}"), TimingRule.INSTANT));
    }

    public MemorysJourney(final MemorysJourney card) {
        super(card);
    }

    @Override
    public MemorysJourney copy() {
        return new MemorysJourney(this);
    }
}

class MemorysJourneyEffect extends OneShotEffect<MemorysJourneyEffect> {

    public MemorysJourneyEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to three target cards from his or her graveyard into his or her library";
    }

    public MemorysJourneyEffect(final MemorysJourneyEffect effect) {
        super(effect);
    }

    @Override
    public MemorysJourneyEffect copy() {
        return new MemorysJourneyEffect(this);
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

class MemorysJourneyTarget extends TargetCard<MemorysJourneyTarget> {

    public MemorysJourneyTarget() {
        super(0, 3, Zone.GRAVEYARD, new FilterCreatureCard());
    }

    public MemorysJourneyTarget(final MemorysJourneyTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getZone(card.getId()) == Zone.GRAVEYARD) {
            UUID firstTarget = source.getFirstTarget();
            if (firstTarget != null && game.getPlayer(firstTarget).getGraveyard().contains(id)) {
                return filter.match(card);
            }
        }
        return false;
    }

    @Override
    public MemorysJourneyTarget copy() {
        return new MemorysJourneyTarget(this);
    }
}
