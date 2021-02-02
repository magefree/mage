
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
public final class ChiefOfTheScale extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Warrior creatures");

    static {
        filter.add(SubType.WARRIOR.getPredicate());
    }

    public ChiefOfTheScale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Warrior creatures you control get +0/+1
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private ChiefOfTheScale(final ChiefOfTheScale card) {
        super(card);
    }

    @Override
    public ChiefOfTheScale copy() {
        return new ChiefOfTheScale(this);
    }
}
