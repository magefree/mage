
package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public final class BreachingLeviathan extends CardImpl {

    public BreachingLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // When Breaching Leviathan enters the battlefield, if you cast it from your hand, tap all nonblue creatures. Those creatures don't untap during their controllers' next untap steps.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BreachingLeviathanEffect(), false),
                CastFromHandSourcePermanentCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, tap all nonblue creatures. Those creatures don't untap during their controllers' next untap steps."),
                new CastFromHandWatcher());
    }

    private BreachingLeviathan(final BreachingLeviathan card) {
        super(card);
    }

    @Override
    public BreachingLeviathan copy() {
        return new BreachingLeviathan(this);
    }
}

class BreachingLeviathanEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblue creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLUE)));
    }

    public BreachingLeviathanEffect() {
        super(Outcome.Tap);
        this.staticText = "tap all nonblue creatures. Those creatures don't untap during their controllers' next untap steps";
    }

    public BreachingLeviathanEffect(final BreachingLeviathanEffect effect) {
        super(effect);
    }

    @Override
    public BreachingLeviathanEffect copy() {
        return new BreachingLeviathanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            creature.tap(source, game);
            doNotUntapNextUntapStep.add(creature);
        }
        if (!doNotUntapNextUntapStep.isEmpty()) {
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
            effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
