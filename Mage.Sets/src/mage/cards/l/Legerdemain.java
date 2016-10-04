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
package mage.cards.l;

import java.util.UUID;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public class Legerdemain extends CardImpl {
    
    private static final FilterPermanent firstFilter = new FilterPermanent("artifact or creature");
    private static final FilterPermanent secondFilter = new FilterPermanent("another permanent that shares the type of artifact or creature");
    static {
        firstFilter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE)));
        secondFilter.add(new AnotherTargetPredicate(2));
        secondFilter.add(new SharesTypePredicate());
    }

    public Legerdemain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of target artifact or creature and another target permanent that shares one of those types with it", false, true));
        TargetPermanent firstTarget = new TargetPermanent(firstFilter);
        firstTarget.setTargetTag(1);
        TargetPermanent secondTarget = new TargetPermanent(secondFilter);
        secondTarget.setTargetTag(2);
        this.getSpellAbility().addTarget(firstTarget);
        this.getSpellAbility().addTarget(secondTarget);
    }

    public Legerdemain(final Legerdemain card) {
        super(card);
    }

    @Override
    public Legerdemain copy() {
        return new Legerdemain(this);
    }
}

class SharesTypePredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<MageItem>> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        if (source != null) {
            if (source.getStackAbility().getTargets().isEmpty()
                || source.getStackAbility().getTargets().get(0).getTargets().isEmpty()) {
                return true;
            }
            Permanent firstPermanent = game.getPermanent(
                    source.getStackAbility().getTargets().get(0).getTargets().get(0));
            Permanent secondPermanent = game.getPermanent(input.getObject().getId());
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
        return true;
    }

    @Override
    public String toString() {
        return "Target permanent that shares the type of artifact or creature";
    }
    
}