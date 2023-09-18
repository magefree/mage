
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class SkittishKavu extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white or blue creature");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.WHITE), new ColorPredicate(ObjectColor.BLUE)));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SkittishKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Skittish Kavu gets +1/+1 as long as no opponent controls a white or blue creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
            new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter, false)),
            "{this} gets +1/+1 as long as no opponent controls a white or blue creature")));
    }

    private SkittishKavu(final SkittishKavu card) {
        super(card);
    }

    @Override
    public SkittishKavu copy() {
        return new SkittishKavu(this);
    }
}
