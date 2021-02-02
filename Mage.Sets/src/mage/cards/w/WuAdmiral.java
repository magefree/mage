
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox
 */
public final class WuAdmiral extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ISLAND, "Island");

    public WuAdmiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Wu Admiral gets +1/+1 as long as an opponent controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
            new OpponentControlsPermanentCondition(filter),
            "{this} gets +1/+1 as long as an opponent controls an Island")));
    }

    private WuAdmiral(final WuAdmiral card) {
        super(card);
    }

    @Override
    public WuAdmiral copy() {
        return new WuAdmiral(this);
    }
}
