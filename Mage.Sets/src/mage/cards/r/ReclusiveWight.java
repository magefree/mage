
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class ReclusiveWight extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public ReclusiveWight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if you control another nonland permanent, sacrifice Reclusive Wight.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "At the beginning of your upkeep, if you control another nonland permanent, sacrifice {this}."
        ));
    }

    private ReclusiveWight(final ReclusiveWight card) {
        super(card);
    }

    @Override
    public ReclusiveWight copy() {
        return new ReclusiveWight(this);
    }
}
