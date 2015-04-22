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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public class Legerdemain extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE)));
    }

    public Legerdemain(UUID ownerId) {
        super(ownerId, 72, "Legerdemain", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        this.expansionSetCode = "TMP";

        // Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of target artifact or creature and another target permanent that shares one of those types with it", false, true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new LegerdemainSecondTarget());
    }

    public Legerdemain(final Legerdemain card) {
        super(card);
    }

    @Override
    public Legerdemain copy() {
        return new Legerdemain(this);
    }
}

class LegerdemainSecondTarget extends TargetPermanent {
    
    LegerdemainSecondTarget() {
        super();
        this.targetName = "another permanent that shares one of those types";
    }

    LegerdemainSecondTarget(final LegerdemainSecondTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent firstPermanent = game.getPermanent(source.getTargets().getFirstTarget());
        Permanent secondPermanent = game.getPermanent(id);
        if (firstPermanent != null && secondPermanent != null) {
            if (firstPermanent.getCardType().contains(CardType.CREATURE) && secondPermanent.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
            if (firstPermanent.getCardType().contains(CardType.ARTIFACT) && secondPermanent.getCardType().contains(CardType.ARTIFACT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LegerdemainSecondTarget copy() {
        return new LegerdemainSecondTarget(this);
    }
}
