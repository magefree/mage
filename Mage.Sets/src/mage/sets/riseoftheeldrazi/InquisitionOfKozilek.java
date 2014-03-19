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

package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class InquisitionOfKozilek extends CardImpl<InquisitionOfKozilek> {

    private static final FilterCard filter = new FilterCard("nonland card with converted mana cost 3 or less");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 4));
    }

    public InquisitionOfKozilek(UUID ownerId){
        super(ownerId, 115, "Inquisition of Kozilek", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY},"{B}");
        this.expansionSetCode = "ROE";
        this.color.setBlack(true);
        this.getSpellAbility().addTarget(new TargetPlayer(true));
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
    }

    public InquisitionOfKozilek(final InquisitionOfKozilek card) {
        super(card);
    }

    @Override
    public InquisitionOfKozilek copy() {
        return new InquisitionOfKozilek(this);
    }
}
