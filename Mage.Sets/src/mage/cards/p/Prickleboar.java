
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Wehk
 */
public final class Prickleboar extends CardImpl {

    public Prickleboar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        /// As long as it's your turn, Prickleboar gets +2/+0
        Effect boostEffect = new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                MyTurnCondition.instance,
                "As long as it's your turn, {this} gets +2/+0");
        // and has first strike.
        Effect gainAbilityEffect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance,
                "and has first strike");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, boostEffect);
        ability.addEffect(gainAbilityEffect);
        this.addAbility(ability);
    }

    public Prickleboar(final Prickleboar card) {
        super(card);
    }

    @Override
    public Prickleboar copy() {
        return new Prickleboar(this);
    }
}
