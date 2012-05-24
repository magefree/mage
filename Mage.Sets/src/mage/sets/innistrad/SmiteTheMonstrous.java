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

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class SmiteTheMonstrous extends CardImpl<SmiteTheMonstrous> {

    public SmiteTheMonstrous(UUID ownerId) {
        super(ownerId, 33, "Smite the Monstrous", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "ISD";

        this.color.setWhite(true);

        // Destroy target creature with power 4 or greater.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterTheMonstrous()));
    }

    public SmiteTheMonstrous(final SmiteTheMonstrous card) {
        super(card);
    }

    @Override
    public SmiteTheMonstrous copy() {
        return new SmiteTheMonstrous(this);
    }
}

class FilterTheMonstrous extends FilterPermanent<FilterTheMonstrous> {

    public FilterTheMonstrous() {
        super("creature with power 4 or greater");
    }

    public FilterTheMonstrous(FilterTheMonstrous filter) {
        super(filter);
    }

    @Override
    public boolean match(Permanent permanent, Game game) {
        if (!super.match(permanent, game))
            return notFilter;

        if (!permanent.getCardType().contains(CardType.CREATURE))
            return notFilter;

        if (permanent.getPower().getValue() < 4)
            return notFilter;

        return !notFilter;
    }

    @Override
    public FilterTheMonstrous copy() {
        return new FilterTheMonstrous(this);
    }
}