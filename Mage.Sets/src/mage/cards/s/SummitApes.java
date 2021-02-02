
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SummitApes extends CardImpl {

    private static final String rule = "As long as you control a Mountain, {this} has menace. (It can't be blocked except by two or more creatures.)";
    private static final FilterLandPermanent filter = new FilterLandPermanent("a Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public SummitApes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.APE);

        this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // As long as you control a Mountain, Summit Apes has menace. (It can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(new MenaceAbility()), new PermanentsOnTheBattlefieldCondition(filter), rule)));
    }

    private SummitApes(final SummitApes card) {
        super(card);
    }

    @Override
    public SummitApes copy() {
        return new SummitApes(this);
    }
}
