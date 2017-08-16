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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldOrCommandZoneCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class ArahboRoarOfTheWorld extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target Cat you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("another Cat you control");

    static {
        filter.add(new SubtypePredicate(SubType.CAT));
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
        filter2.add(new SubtypePredicate(SubType.CAT));
        filter2.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new AnotherPredicate());
    }

    public ArahboRoarOfTheWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Cat");
        this.subtype.add("Avatar");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Eminence &mdash; At the beginning of combat on your turn, if Arahbo, Roar of the World is in the command zone or on the battlefield, another target Cat you control gets +3/+3 until end of turn.
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(Zone.ALL, new BoostTargetEffect(3, 3, Duration.EndOfTurn), TargetController.YOU, false, false),
                SourceOnBattlefieldOrCommandZoneCondition.instance,
                "<i>Eminence</i> - At the beginning of combat on your turn, if Arahbo, Roar of the World is in the command zone or on the battlefield, another target Cat you control gets +3/+3 until end of turn.");
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);

        // Whenever another Cat you control attacks, you may pay {1}{G}{W}. If you do, it gains trample and gets +X/+X until end of turn, where X is its power.
//        Effect effect = new DoIfCostPaid(new ArahboEffect(), new ManaCostsImpl("{1}{G}{W}"));
        ability = new AttacksCreatureYouControlTriggeredAbility(
                new DoIfCostPaid(new ArahboEffect(), new ManaCostsImpl("{1}{G}{W}")), false, filter2, true);
        this.addAbility(ability);
    }

    public ArahboRoarOfTheWorld(final ArahboRoarOfTheWorld card) {
        super(card);
    }

    @Override
    public ArahboRoarOfTheWorld copy() {
        return new ArahboRoarOfTheWorld(this);
    }
}

class ArahboEffect extends OneShotEffect {

    public ArahboEffect() {
        super(Outcome.Benefit);
        this.staticText = "it gains trample and gets +X/+X until end of turn, where X is its power";
    }

    public ArahboEffect(final ArahboEffect effect) {
        super(effect);
    }

    @Override
    public ArahboEffect copy() {
        return new ArahboEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null && creature.isCreature()) {
            int pow = creature.getPower().getValue();
            ContinuousEffect effect = new BoostTargetEffect(pow, pow, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature, game));
            game.addEffect(effect, source);
            effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
