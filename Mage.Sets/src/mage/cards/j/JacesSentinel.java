
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class JacesSentinel extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Jace planeswalker");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.JACE));
    }

    public JacesSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As long as you control a Jace planeswalker, Jace's Sentinel gets +1/+0 and can't be blocked.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter),
                "As long as you control a Jace planeswalker, {this} gets +1/+0"));
        ability.addEffect(new ConditionalContinuousEffect(
                new CantBeBlockedSourceEffect(),
                new PermanentsOnTheBattlefieldCondition(filter),
                "and can't be blocked"));
        this.addAbility(ability);
    }

    public JacesSentinel(final JacesSentinel card) {
        super(card);
    }

    @Override
    public JacesSentinel copy() {
        return new JacesSentinel(this);
    }
}
