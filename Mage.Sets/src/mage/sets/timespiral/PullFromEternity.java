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
package mage.sets.timespiral;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public class PullFromEternity extends CardImpl {

    private static final FilterCard filter = new FilterCard("face-up exiled card");

    static {
        filter.add(Predicates.not(new FaceDownPredicate()));
    }

    public PullFromEternity(UUID ownerId) {
        super(ownerId, 35, "Pull from Eternity", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "TSP";


        // Put target face-up exiled card into its owner's graveyard.
        this.getSpellAbility().addEffect(new PullFromEternityEffect());
        this.getSpellAbility().addTarget(new TargetCardInExile(1,1,filter, null, true));

    }

    public PullFromEternity(final PullFromEternity card) {
        super(card);
    }

    @Override
    public PullFromEternity copy() {
        return new PullFromEternity(this);
    }
}

class PullFromEternityEffect extends OneShotEffect {

    public PullFromEternityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target face-up exiled card into its owner's graveyard.";
    }

    public PullFromEternityEffect(final PullFromEternityEffect effect) {
        super(effect);
    }

    @Override
    public PullFromEternityEffect copy() {
        return new PullFromEternityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.EXILED);
            }
            return true;
        }
        return false;
    }
}
