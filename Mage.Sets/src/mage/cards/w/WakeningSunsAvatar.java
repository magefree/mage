
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author TheElk801
 */
public final class WakeningSunsAvatar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Dinosaur creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.DINOSAUR)));
    }

    public WakeningSunsAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Wakening Sun's Avatar enters the battlefield, if you cast it from you hand, destroy all non-Dinosaur creatures.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter), false),
                CastFromHandSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, destroy all non-Dinosaur creatures."),
                new CastFromHandWatcher());
    }

    public WakeningSunsAvatar(final WakeningSunsAvatar card) {
        super(card);
    }

    @Override
    public WakeningSunsAvatar copy() {
        return new WakeningSunsAvatar(this);
    }
}
