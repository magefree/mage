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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.token.SpiderToken;

/**
 *
 * @author fireshoes
 */
public class Arachnogenesis extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Spider creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Spider")));
    }

    public Arachnogenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Put X 1/2 green Spider creature tokens with reach onto the battlefield, where X is the number of creatures attacking you.
        Effect effect = new CreateTokenEffect(new SpiderToken(), new ArachnogenesisCount());
        effect.setText("Put X 1/2 green Spider creature tokens with reach onto the battlefield, where X is the number of creatures attacking you");
        this.getSpellAbility().addEffect(effect);
        
        // Prevent all combat damage that would be dealt this turn by non-Spider creatures.
         this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    public Arachnogenesis(final Arachnogenesis card) {
        super(card);
    }

    @Override
    public Arachnogenesis copy() {
        return new Arachnogenesis(this);
    }
}

class ArachnogenesisCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getDefenderId().equals(sourceAbility.getControllerId())) {
                count += combatGroup.getAttackers().size();
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creatures attacking you";
    }
}