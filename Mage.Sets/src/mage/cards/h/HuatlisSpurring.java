
package mage.cards.h;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class HuatlisSpurring extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Jace planeswalker");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.HUATLI.getPredicate());
    }

    public HuatlisSpurring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +2/+0 until end of turn. If you control a Huatli planeswalker, that creature gets +4/+0 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(4, 0, Duration.EndOfTurn),
                new BoostTargetEffect(2, 0, Duration.EndOfTurn),
                new PermanentsOnTheBattlefieldCondition(filter),
                "Target creature gets +2/+0 until end of turn. If you control a Huatli planeswalker, that creature gets +4/+0 until end of turn instead."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HuatlisSpurring(final HuatlisSpurring card) {
        super(card);
    }

    @Override
    public HuatlisSpurring copy() {
        return new HuatlisSpurring(this);
    }
}
