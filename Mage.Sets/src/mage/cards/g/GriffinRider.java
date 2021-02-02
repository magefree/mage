
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class GriffinRider extends CardImpl {

    private static final String rule1 = "As long as you control a Griffin creature, {this} gets +3/+3.";
    private static final String rule2 = "As long as you control a Griffin creature, {this} has flying.";
    private static final FilterPermanent filterGriffinCard = new FilterCreaturePermanent();

    static {
        filterGriffinCard.add(SubType.GRIFFIN.getPredicate());
    }

    public GriffinRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as you control a Griffin creature, Griffin Rider gets +3/+3 and has flying.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(filterGriffinCard), rule1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect1));
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filterGriffinCard), rule2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect2));
    }

    private GriffinRider(final GriffinRider card) {
        super(card);
    }

    @Override
    public GriffinRider copy() {
        return new GriffinRider(this);
    }
}
