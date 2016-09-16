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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class ShrewdNegotiation extends CardImpl {

    private static final String rule = "Exchange control of target artifact you control and target artifact or creature you don't control";

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                        new CardTypePredicate(CardType.CREATURE)));
    }

    public ShrewdNegotiation(UUID ownerId) {
        super(ownerId, 64, "Shrewd Negotiation", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{U}");
        this.expansionSetCode = "KLD";

        // Exchange control of target artifact you control and target artifact or creature you don't control.
        getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true));
        getSpellAbility().addTarget(new TargetControlledPermanent(new FilterControlledArtifactPermanent("artifact you control")));
        getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public ShrewdNegotiation(final ShrewdNegotiation card) {
        super(card);
    }

    @Override
    public ShrewdNegotiation copy() {
        return new ShrewdNegotiation(this);
    }
}
