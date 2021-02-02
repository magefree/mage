
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author L_J
 */
public final class DireWolves extends CardImpl {

    private static final String rule = "{this} has banding as long as you control a Plains.";
    private static final FilterLandPermanent filter = new FilterLandPermanent("a Plains");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public DireWolves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Dire Wolves has banding as long as you control a Plains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(BandingAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule)));
    }

    private DireWolves(final DireWolves card) {
        super(card);
    }

    @Override
    public DireWolves copy() {
        return new DireWolves(this);
    }
}
