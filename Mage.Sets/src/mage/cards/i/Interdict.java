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
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class Interdict extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated ability from an artifact, creature, enchantment, or land");

    static {
        filter.add(new InterdictPredicate());
    }

    public Interdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target activated ability from an artifact, creature, enchantment, or land. That permanent's activated abilities can't be activated this turn.
        this.getSpellAbility().addEffect(new InterdictCounterEffect());
        this.getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("<br><br>Draw a card"));
    }

    public Interdict(final Interdict card) {
        super(card);
    }

    @Override
    public Interdict copy() {
        return new Interdict(this);
    }
}

class InterdictPredicate implements Predicate<Ability> {

    public InterdictPredicate() {
    }

    @Override
    public boolean apply(Ability input, Game game) {
        if (input instanceof StackAbility && input.getAbilityType() == AbilityType.ACTIVATED) {
            MageObject sourceObject = input.getSourceObject(game);
            if (sourceObject != null) {
                return (sourceObject.isArtifact()
                        || sourceObject.isEnchantment()
                        || sourceObject.isCreature()
                        || sourceObject.isLand());
            }
        }
        return false;
    }
}

class InterdictCounterEffect extends OneShotEffect {

    public InterdictCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target activated ability from an artifact, creature, enchantment, or land. That permanent's activated abilities can't be activated this turn.";
    }

    public InterdictCounterEffect(final InterdictCounterEffect effect) {
        super(effect);
    }

    @Override
    public InterdictCounterEffect copy() {
        return new InterdictCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject != null && game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
            Permanent sourcePermanent = stackObject.getStackAbility().getSourcePermanentIfItStillExists(game);
            if (sourcePermanent != null) {
                InterdictCantActivateEffect effect = new InterdictCantActivateEffect();
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.getContinuousEffects().addEffect(effect, source);
            }
            return true;
        }
        return false;
    }

}

class InterdictCantActivateEffect extends RestrictionEffect {

    public InterdictCantActivateEffect() {
        super(Duration.EndOfTurn);
        staticText = "That permanent's activated abilities can't be activated this turn";
    }

    public InterdictCantActivateEffect(final InterdictCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public InterdictCantActivateEffect copy() {
        return new InterdictCantActivateEffect(this);
    }

}
