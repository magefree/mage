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
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControlledFromStartOfControllerTurnPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author MTGfan & L_J
 */
public class Norritt extends CardImpl {

    private static final FilterCreaturePermanent filterBlue = new FilterCreaturePermanent("blue creature");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("non-Wall creature");

    static {
        filterCreature.add(Predicates.not(new SubtypePredicate(SubType.WALL)));
        filterCreature.add(new ControlledFromStartOfControllerTurnPredicate());
        filterCreature.add(new ControllerPredicate(TargetController.ACTIVE));
        filterCreature.setMessage("non-Wall creature the active player has controlled continuously since the beginning of the turn.");
    }

    public Norritt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Untap target blue creature.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        ability1.addTarget(new TargetCreaturePermanent(filterBlue));
        this.addAbility(ability1);

        // {T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only before attackers are declared.
        Ability ability2 = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn),
                new TapSourceCost(), BeforeAttackersAreDeclaredCondition.instance,
                "{T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. "
                + "That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. "
                + "Activate this ability only before attackers are declared.");
        ability2.addEffect(new NorrittDelayedDestroyEffect());
        ability2.addTarget(new TargetCreaturePermanent(filterCreature));
        this.addAbility(ability2, new AttackedThisTurnWatcher());

    }

    public Norritt(final Norritt card) {
        super(card);
    }

    @Override
    public Norritt copy() {
        return new Norritt(this);
    }
}

class NorrittDelayedDestroyEffect extends OneShotEffect {

    public NorrittDelayedDestroyEffect() {
        super(Outcome.Detriment);
        this.staticText = "If it doesn't, destroy it at the beginning of the next end step";
    }

    public NorrittDelayedDestroyEffect(final NorrittDelayedDestroyEffect effect) {
        super(effect);
    }

    @Override
    public NorrittDelayedDestroyEffect copy() {
        return new NorrittDelayedDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DestroyTargetEffect effect = new DestroyTargetEffect();
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility
                = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone.ALL, effect, TargetController.ANY, new InvertCondition(TargetAttackedThisTurnCondition.instance));
        delayedAbility.getDuration();
        delayedAbility.getTargets().addAll(source.getTargets());
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
