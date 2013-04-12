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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class CruelRevival extends CardImpl<CruelRevival> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creature");
    private final static FilterCard filter2 = new FilterCard("Zombie card from your graveyard");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(new SubtypePredicate("Zombie")));
        filter2.add(new SubtypePredicate("Zombie"));
    }

    public CruelRevival(UUID ownerId) {
        super(ownerId, 135, "Cruel Revival", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{B}");
        this.expansionSetCode = "ONS";

        this.color.setBlack(true);

        // Destroy target non-Zombie creature. It can't be regenerated. Return up to one target Zombie card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CruelRevivalEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
    }

    public CruelRevival(final CruelRevival card) {
        super(card);
    }

    @Override
    public CruelRevival copy() {
        return new CruelRevival(this);
    }
}

class CruelRevivalEffect extends OneShotEffect<CruelRevivalEffect> {

    public CruelRevivalEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Destroy target non-Zombie creature. It can't be regenerated. Return up to one target Zombie card from your graveyard to your hand";
    }

    public CruelRevivalEffect(final CruelRevivalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetDestroy = game.getPermanent(source.getFirstTarget());
        Card targetRetrieve = game.getCard(source.getTargets().get(1).getFirstTarget());
        if (targetDestroy != null) {
            targetDestroy.destroy(source.getId(), game, true);
        }
        if (targetRetrieve != null) {
            targetRetrieve.moveToZone(Constants.Zone.HAND, source.getId(), game, true);
        }
        return true;
    }

    @Override
    public CruelRevivalEffect copy() {
        return new CruelRevivalEffect(this);
    }
}