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
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public class BlazeOfGlory extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(new BlazeOfGloryDefendingPlayerControlsPredicate());
    }

    public BlazeOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Cast Blaze of Glory only during combat before blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, BeforeBlockersAreDeclaredCondition.instance));

        // Target creature defending player controls can block any number of creatures this turn. It blocks each attacking creature this turn if able.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new CanBlockAdditionalCreatureTargetEffect(Duration.EndOfTurn, 0));
        this.getSpellAbility().addEffect(new BlazeOfGloryRequirementEffect());
    }

    public BlazeOfGlory(final BlazeOfGlory card) {
        super(card);
    }

    @Override
    public BlazeOfGlory copy() {
        return new BlazeOfGlory(this);
    }
}

class BlazeOfGloryDefendingPlayerControlsPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        return game.getCombat().getPlayerDefenders(game).contains(input.getObject().getControllerId());
    }
}

class BlazeOfGloryRequirementEffect extends RequirementEffect {

    public BlazeOfGloryRequirementEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "It blocks each attacking creature this turn if able";
    }

    public BlazeOfGloryRequirementEffect(final BlazeOfGloryRequirementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(targetPointer.getFirst(game, source));
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public boolean mustBlockAllAttackers(Game game) {
        return true;
    }

    @Override
    public BlazeOfGloryRequirementEffect copy() {
        return new BlazeOfGloryRequirementEffect(this);
    }

}
