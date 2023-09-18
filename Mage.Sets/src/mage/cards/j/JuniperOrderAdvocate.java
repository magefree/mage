
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class JuniperOrderAdvocate extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public JuniperOrderAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // As long as Juniper Order Advocate is untapped, green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false),
            SourceTappedCondition.UNTAPPED,
            "As long as {this} is untapped, green creatures you control get +1/+1.")));
    }

    private JuniperOrderAdvocate(final JuniperOrderAdvocate card) {
        super(card);
    }

    @Override
    public JuniperOrderAdvocate copy() {
        return new JuniperOrderAdvocate(this);
    }
}
