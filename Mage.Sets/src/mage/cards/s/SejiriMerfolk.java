
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North, nantuko
 */
public final class SejiriMerfolk extends CardImpl {

    private static final String rule1 = "As long as you control a Plains, {this} has first strike.";
    private static final String rule2 = "As long as you control a Plains, {this} has lifelink.";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Plains");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
    }

    public SejiriMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect1));
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect2));

    }

    public SejiriMerfolk(final SejiriMerfolk card) {
        super(card);
    }

    @Override
    public SejiriMerfolk copy() {
        return new SejiriMerfolk(this);
    }
}
