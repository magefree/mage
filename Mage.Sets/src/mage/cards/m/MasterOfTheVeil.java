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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class MasterOfTheVeil extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a morph ability");

    static {
        filter.add(new AbilityPredicate(MorphAbility.class));
    }

    public MasterOfTheVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Morph {2}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{U}")));

        // When Master of the Veil is turned face up, you may turn target creature with a morph ability face down.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new MasterOfTheVeilEffect(), false, true);
        ability.addTarget(new TargetCreaturePermanent(1, 1, filter, false));
        this.addAbility(ability);
    }

    public MasterOfTheVeil(final MasterOfTheVeil card) {
        super(card);
    }

    @Override
    public MasterOfTheVeil copy() {
        return new MasterOfTheVeil(this);
    }
}

class MasterOfTheVeilEffect extends OneShotEffect {

    MasterOfTheVeilEffect() {
        super(Outcome.Benefit);
        this.staticText = "turn target creature with a morph ability face down";
    }

    MasterOfTheVeilEffect(final MasterOfTheVeilEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfTheVeilEffect copy() {
        return new MasterOfTheVeilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
            }
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(pred);
        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        return true;
    }
}
