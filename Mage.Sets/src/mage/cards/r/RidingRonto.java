
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Styxo
 */
public final class RidingRonto extends CardImpl {

    public RidingRonto(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {3}{W}{W}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{3}{W}{W}", 3));

        // As long as Riding Ronto is monstrous, it has vigilance
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield),
                MonstrousCondition.instance,
                "As long as Riding Ronto is monstrous, it has vigilance")
        ));

    }

    private RidingRonto(final RidingRonto card) {
        super(card);
    }

    @Override
    public RidingRonto copy() {
        return new RidingRonto(this);
    }
}
