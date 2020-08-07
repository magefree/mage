package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpitefulPrankster extends CardImpl {

    public SpitefulPrankster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As long as it's your turn, Spiteful Prankster has first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "As long as it's your turn, {this} has first strike."
        )).addHint(MyTurnHint.instance));

        // Whenever another creature dies, Spiteful Prankster deals 1 damage to target player or planeswalker.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DamageTargetEffect(1), false, true
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private SpitefulPrankster(final SpitefulPrankster card) {
        super(card);
    }

    @Override
    public SpitefulPrankster copy() {
        return new SpitefulPrankster(this);
    }
}
