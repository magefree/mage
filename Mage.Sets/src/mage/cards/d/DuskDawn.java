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

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author stravant
 */


public class DuskDawn extends SplitCard {
    private static final FilterCreaturePermanent filterCreatures3orGreater = new FilterCreaturePermanent("creatures with power greater than or equal to 3");
    static {
        filterCreatures3orGreater.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public DuskDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}","{3}{W}{W}",false);

        // Dusk
        // Destroy all creatures with power 3 or greater.
        Effect destroy = new DestroyAllEffect(filterCreatures3orGreater);
        destroy.setText("Destroy all creatures with power greater than or equal to 3.");
        getLeftHalfCard().getSpellAbility().addEffect(destroy);

        // Dawn
        // Return all creature cards with power less than or equal to 2 from your graveyard to your hand.
        ((CardImpl)(getRightHalfCard())).addAbility(new AftermathAbility());
        getRightHalfCard().getSpellAbility().addEffect(new DawnEffect());

    }

    public DuskDawn(final DuskDawn card) {
        super(card);
    }

    @Override
    public DuskDawn copy() {
        return new DuskDawn(this);
    }
}

class DawnEffect extends OneShotEffect {

    private static final FilterCard filter2orLess = new FilterCreatureCard("creatures with power less than or equal to 2");
    static {
        filter2orLess.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    DawnEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return all creature cards with power 2 or less from your graveyard to your hand.";
    }

    DawnEffect(final DawnEffect effect) {
        super(effect);
    }

    @Override
    public DawnEffect copy() {
        return new DawnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Set<Card> cards = player.getGraveyard().getCards(filter2orLess, game);
            player.moveCards(cards, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
