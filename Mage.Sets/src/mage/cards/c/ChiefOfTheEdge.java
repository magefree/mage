
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ChiefOfTheEdge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Warrior creatures");

    static {
        filter.add(SubType.WARRIOR.getPredicate());
    }

    public ChiefOfTheEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Other Warrior creatures you control get +1/+0
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter, true)));
    }

    private ChiefOfTheEdge(final ChiefOfTheEdge card) {
        super(card);
    }

    @Override
    public ChiefOfTheEdge copy() {
        return new ChiefOfTheEdge(this);
    }
}
