
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class VexingBeetle extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public VexingBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vexing Beetle can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCounteredSourceEffect()));

        // Vexing Beetle gets +3/+3 as long as no opponent controls a creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield),
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                "{this} gets +3/+3 as long as no opponent controls a creature")
        ));
    }

    public VexingBeetle(final VexingBeetle card) {
        super(card);
    }

    @Override
    public VexingBeetle copy() {
        return new VexingBeetle(this);
    }
}
