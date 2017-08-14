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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutPermanentOnBattlefieldEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class Dermoplasm extends CardImpl {

    public Dermoplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add("Shapeshifter");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Morph {2}{U}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{U}{U}")));

        // When Dermoplasm is turned face up, you may put a creature card with a morph ability from your hand onto the battlefield face up. If you do, return Dermoplasm to its owner's hand.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new DermoplasmEffect()));
    }

    public Dermoplasm(final Dermoplasm card) {
        super(card);
    }

    @Override
    public Dermoplasm copy() {
        return new Dermoplasm(this);
    }
}

class DermoplasmEffect extends OneShotEffect {

    public DermoplasmEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card with a morph ability from your hand onto the battlefield face up. If you do, return {this} to its owner's hand";
    }

    public DermoplasmEffect(final DermoplasmEffect effect) {
        super(effect);
    }

    @Override
    public DermoplasmEffect copy() {
        return new DermoplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thisCreature = game.getPermanent(source.getId());
        FilterCreatureCard filter = new FilterCreatureCard("a creature card with a morph ability");
        filter.add(new AbilityPredicate(MorphAbility.class));
        Effect effect = new PutPermanentOnBattlefieldEffect(new FilterCreatureCard(filter));
        if (effect.apply(game, source)) {
            if (thisCreature != null) {
                effect = new ReturnToHandTargetEffect();
                effect.setTargetPointer(new FixedTarget(thisCreature.getId()));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
