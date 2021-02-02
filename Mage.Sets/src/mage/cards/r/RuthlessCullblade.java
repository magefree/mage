
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class RuthlessCullblade extends CardImpl {

    public RuthlessCullblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ruthless Cullblade gets +2/+1 as long as an opponent has 10 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield),
                new XorLessLifeCondition(XorLessLifeCondition.CheckType.AN_OPPONENT, 10),
                "{this} gets +2/+1 as long as an opponent has 10 or less life.")));
    }

    private RuthlessCullblade(final RuthlessCullblade card) {
        super(card);
    }

    @Override
    public RuthlessCullblade copy() {
        return new RuthlessCullblade(this);
    }
}
