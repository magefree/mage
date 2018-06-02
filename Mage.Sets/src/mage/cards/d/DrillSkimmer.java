
package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author wetterlicht
 */
public final class DrillSkimmer extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("you control another artifact creature");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public DrillSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Drill-Skimmer has shroud as long as you control another artifact creature.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield),
                condition, "{this} has shroud as long as you control another artifact creature.")));
    }

    public DrillSkimmer(final DrillSkimmer card) {
        super(card);
    }

    @Override
    public DrillSkimmer copy() {
        return new DrillSkimmer(this);
    }
}
