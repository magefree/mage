
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldOrCommandZoneCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class ArahboRoarOfTheWorld extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target Cat you control");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("another Cat you control");

    static {
        filter.add(SubType.CAT.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter2.add(SubType.CAT.getPredicate());
        filter2.add(TargetController.YOU.getControllerPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public ArahboRoarOfTheWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT, SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Eminence &mdash; At the beginning of combat on your turn, if Arahbo, Roar of the World is in the command zone or on the battlefield, another target Cat you control gets +3/+3 until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(Zone.ALL, new BoostTargetEffect(3, 3, Duration.EndOfTurn), TargetController.YOU, false, false),
                SourceOnBattlefieldOrCommandZoneCondition.instance,
                "At the beginning of combat on your turn, if {this} is in the command zone or on the battlefield, another target Cat you control gets +3/+3 until end of turn.");
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);

        // Whenever another Cat you control attacks, you may pay {1}{G}{W}. If you do, it gains trample and gets +X/+X until end of turn, where X is its power.
//        Effect effect = new DoIfCostPaid(new ArahboEffect(), new ManaCostsImpl("{1}{G}{W}"));
        ability = new AttacksCreatureYouControlTriggeredAbility(
                new DoIfCostPaid(new ArahboEffect(), new ManaCostsImpl("{1}{G}{W}")), false, filter2, true);
        this.addAbility(ability);
    }

    private ArahboRoarOfTheWorld(final ArahboRoarOfTheWorld card) {
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
        if (creature != null && creature.isCreature(game)) {
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
