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
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllThatAttackedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class FuryOfTheHorde extends CardImpl {

    private static final FilterCard filter = new FilterCard("two red cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public FuryOfTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");

        // You may exile two red cards from your hand rather than pay Fury of the Horde's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));

        // Untap all creatures that attacked this turn. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getSpellAbility().addEffect(new UntapAllThatAttackedEffect());
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect());
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());

    }

    public FuryOfTheHorde(final FuryOfTheHorde card) {
        super(card);
    }

    @Override
    public FuryOfTheHorde copy() {
        return new FuryOfTheHorde(this);
    }
}
