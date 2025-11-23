package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkasCharge extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ALLY);

    public SokkasCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // During your turn, Allies you control have double strike and lifelink.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                MyTurnCondition.instance, "during your turn, Allies you control have double strike"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                MyTurnCondition.instance, "and lifelink"
        ));
        this.addAbility(ability);
    }

    private SokkasCharge(final SokkasCharge card) {
        super(card);
    }

    @Override
    public SokkasCharge copy() {
        return new SokkasCharge(this);
    }
}
