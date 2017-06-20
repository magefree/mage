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
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class NemesisTrap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("If a white creature is attacking");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(new AttackingPredicate());
    }

    public NemesisTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}{B}");
        this.subtype.add("Trap");

        // If a white creature is attacking, you may pay {B}{B} rather than pay Nemesis Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{B}{B}"), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 0, false)));

        // Exile target attacking creature. Create a token that's a copy of that creature. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new NemesisTrapEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    public NemesisTrap(final NemesisTrap card) {
        super(card);
    }

    @Override
    public NemesisTrap copy() {
        return new NemesisTrap(this);
    }
}

class NemesisTrapEffect extends OneShotEffect {

    public NemesisTrapEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target attacking creature. Create a token that's a copy of that creature. Exile it at the beginning of the next end step";
    }

    public NemesisTrapEffect(final NemesisTrapEffect effect) {
        super(effect);
    }

    @Override
    public NemesisTrapEffect copy() {
        return new NemesisTrapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedCreature = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetedCreature != null) {
            // exile target
            controller.moveCards(targetedCreature, Zone.EXILED, source, game);
            // create token
            PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect();
            effect.setTargetPointer(new FixedTarget(targetedCreature, game));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanent()) {
                Effect exileEffect = new ExileTargetEffect("Exile " + addedToken.getName() + " at the beginning of the next end step");
                exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }
}
