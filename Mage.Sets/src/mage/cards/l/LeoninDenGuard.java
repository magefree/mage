
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class LeoninDenGuard extends CardImpl {

    private static final String rule1 = "As long as {this} is equipped, it gets +1/+1";
    private static final String rule2 = "As long as {this} is equipped, it has vigilance";

    public LeoninDenGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As long as Leonin Den-Guard is equipped, it gets +1/+1 and has vigilance.        
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), EquippedSourceCondition.instance, rule1);
        this.addAbility(new SimpleStaticAbility(effect1));
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()), EquippedSourceCondition.instance, rule2);
        this.addAbility(new SimpleStaticAbility(effect2));

    }

    private LeoninDenGuard(final LeoninDenGuard card) {
        super(card);
    }

    @Override
    public LeoninDenGuard copy() {
        return new LeoninDenGuard(this);
    }
}
