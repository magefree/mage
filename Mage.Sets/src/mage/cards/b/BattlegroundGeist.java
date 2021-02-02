
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author nantuko
 */
public final class BattlegroundGeist extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Spirit creatures");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public BattlegroundGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Other Spirit creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter, true)));
    }

    private BattlegroundGeist(final BattlegroundGeist card) {
        super(card);
    }

    @Override
    public BattlegroundGeist copy() {
        return new BattlegroundGeist(this);
    }
}
