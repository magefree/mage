package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetStackObject;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Rafbill
 */
public final class NotOfThisWorld extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spell or ability that targets a permanent you control");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterControlledPermanent()));
    }

    public NotOfThisWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{7}");
        this.subtype.add(SubType.ELDRAZI);

        // Counter target spell or ability that targets a permanent you control.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));

        // Not of This World costs {7} less to cast if it targets a spell or ability that targets a creature you control with power 7 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(7, NotOfThisWorldCondition.instance).setCanWorksOnStackOnly(true))
        );
    }

    private NotOfThisWorld(final NotOfThisWorld card) {
        super(card);
    }

    @Override
    public NotOfThisWorld copy() {
        return new NotOfThisWorld(this);
    }
}

enum NotOfThisWorldCondition implements Condition {

    instance;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell == null || !sourceSpell.getStackAbility().getTargets().isChosen()) {
            return false;
        }
        StackObject objectToCounter = game.getStack().getStackObject(sourceSpell.getStackAbility().getTargets().getFirstTarget());
        if (objectToCounter == null) {
            return false;
        }
        return objectToCounter
                .getStackAbility()
                .getModes()
                .values()
                .stream()
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .filter(target -> !target.isNotTarget())
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanentOrLKIBattlefield)
                .anyMatch(permanent -> permanent != null && filter.match(
                        permanent, sourceSpell.getControllerId(), source, game
                ));
    }

    @Override
    public String toString() {
        return "it targets a spell or ability that targets a creature you control with power 7 or greater";
    }

}
