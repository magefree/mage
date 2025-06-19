
package mage.cards.z;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZealotsEnDal extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("all nonland permanents you control are white");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public ZealotsEnDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if all nonland permanents you control are white, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(1)).withInterveningIf(condition));
    }

    private ZealotsEnDal(final ZealotsEnDal card) {
        super(card);
    }

    @Override
    public ZealotsEnDal copy() {
        return new ZealotsEnDal(this);
    }
}
