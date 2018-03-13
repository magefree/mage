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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BrokenVisageSpiritToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class BrokenVisage extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact attacking creature");
    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        filter.add(new AttackingPredicate());
    }

    public BrokenVisage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Destroy target nonartifact attacking creature. It can't be regenerated. Create a black Spirit creature token. Its power is equal to that creature's power and its toughness is equal to that creature's toughness. Sacrifice the token at the beginning of the next end step.
        this.getSpellAbility().addEffect(new BrokenVisageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public BrokenVisage(final BrokenVisage card) {
        super(card);
    }

    @Override
    public BrokenVisage copy() {
        return new BrokenVisage(this);
    }
}

class BrokenVisageEffect extends OneShotEffect {

    public BrokenVisageEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target nonartifact attacking creature. It can't be regenerated. Create a black Spirit creature token. Its power is equal to that creature's power and its toughness is equal to that creature's toughness. Sacrifice the token at the beginning of the next end step";
    }

    public BrokenVisageEffect(final BrokenVisageEffect effect) {
        super(effect);
    }

    @Override
    public BrokenVisageEffect copy() {
        return new BrokenVisageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) { 
            permanent.destroy(source.getSourceId(), game, true);
            CreateTokenEffect effect = new CreateTokenEffect(new BrokenVisageSpiritToken(permanent.getPower().getValue(), permanent.getToughness().getValue()));
            effect.apply(game, source);
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice the token at the beginning of the next end step", source.getControllerId());
                    sacrificeEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
        }
        return true;
    }

}
