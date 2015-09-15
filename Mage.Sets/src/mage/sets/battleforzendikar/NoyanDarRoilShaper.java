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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class NoyanDarRoilShaper extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public NoyanDarRoilShaper(UUID ownerId) {
        super(ownerId, 216, "Noyan Dar, Roil Shaper", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        this.expansionSetCode = "BFZ";
        this.supertype.add("Legendary");
        this.subtype.add("Merfolk");
        this.subtype.add("Ally");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell, you may put three +1/+1 counters on target land you control. 
        // If you do, that land becomes a 0/0 Elemental creature with haste that's still a land.
        Ability ability = new SpellCastControllerTriggeredAbility(new NoyanDarEffect(), filter, false);
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    public NoyanDarRoilShaper(final NoyanDarRoilShaper card) {
        super(card);
    }

    @Override
    public NoyanDarRoilShaper copy() {
        return new NoyanDarRoilShaper(this);
    }
}

class NoyanDarEffect extends OneShotEffect {

    public NoyanDarEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "put three +1/+1 counters on target land you control. If you do, that land becomes a 0/0 Elemental creature with haste that's still a land.";
    }

    public NoyanDarEffect(final NoyanDarEffect effect) {
        super(effect);
    }

    @Override
    public NoyanDarEffect copy() {
        return new NoyanDarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = null;
        for (Target target : source.getTargets()) {
            targetId = target.getFirstTarget();
        }
        if (targetId != null) {
            FixedTarget fixedTarget = new FixedTarget(targetId);
            ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(new AwakenElementalToken(), false, true, Duration.Custom);
            continuousEffect.setTargetPointer(fixedTarget);
            game.addEffect(continuousEffect, source);
            Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(3));
            effect.setTargetPointer(fixedTarget);
            return effect.apply(game, source);
        }
        return true;
    }
}


class AwakenElementalToken extends Token {

    public AwakenElementalToken() {
        super("", "0/0 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add("Elemental");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
    }
}
