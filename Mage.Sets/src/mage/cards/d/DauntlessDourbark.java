
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class DauntlessDourbark extends CardImpl {

    static final private FilterControlledPermanent filter = new FilterControlledPermanent("Forests you control plus the number of Treefolk you control");
    static final private FilterControlledPermanent filter2 = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(),
                SubType.TREEFOLK.getPredicate()));
        filter2.add(SubType.TREEFOLK.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }
    
    static final private String rule = "{this} has trample as long as you control another Treefolk";

    public DauntlessDourbark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Dauntless Dourbark's power and toughness are each equal to the number of Forests you control plus the number of Treefolk you control.
        DynamicValue amount = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(amount, Duration.EndOfGame)));
        
        // Dauntless Dourbark has trample as long as you control another Treefolk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(filter2), rule)));
        
    }

    private DauntlessDourbark(final DauntlessDourbark card) {
        super(card);
    }

    @Override
    public DauntlessDourbark copy() {
        return new DauntlessDourbark(this);
    }
}
