
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class SkyshroudElite extends CardImpl {

    public SkyshroudElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Skyshroud Elite gets +1/+2 as long as an opponent controls a nonbasic land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
                new OpponentControlsPermanentCondition(FilterLandPermanent.nonbasicLand()),
                "{this} gets +1/+2 as long as an opponent controls a nonbasic land")));
    }

    private SkyshroudElite(final SkyshroudElite card) {
        super(card);
    }

    @Override
    public SkyshroudElite copy() {
        return new SkyshroudElite(this);
    }
}
