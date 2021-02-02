
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class ZealotsEnDal extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public ZealotsEnDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if all nonland permanents you control are white, you gain 1 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(1), TargetController.YOU, false),
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                "At the beginning of your upkeep, if all nonland permanents you control are white, you gain 1 life."
        ));
    }

    private ZealotsEnDal(final ZealotsEnDal card) {
        super(card);
    }

    @Override
    public ZealotsEnDal copy() {
        return new ZealotsEnDal(this);
    }
}
