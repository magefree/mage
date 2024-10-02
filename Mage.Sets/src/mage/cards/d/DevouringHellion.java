package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DevourEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevouringHellion extends CardImpl {

    public DevouringHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Devouring Hellion enters the battlefield, you may sacrifice any number of creatures and/or planeswalkers. If you do, it enters with twice that many +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new DevourEffect(2, StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER)
                        .setText("As {this} enters, you may sacrifice any number of creatures and/or planeswalkers."
                                + " If you do, it enters with twice that many +1/+1 counters on it")
        ));
    }

    private DevouringHellion(final DevouringHellion card) {
        super(card);
    }

    @Override
    public DevouringHellion copy() {
        return new DevouringHellion(this);
    }
}
