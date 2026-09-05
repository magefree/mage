package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LakeTownToymaker extends CardImpl {

    public LakeTownToymaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, if you've drawn two or more cards this turn, another target creature you control gets +3/+0 and gains first strike until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new ConditionalContinuousEffect(
            new BoostTargetEffect(3, 0),
            DrewTwoOrMoreCardsCondition.instance,
            "if you've drawn two or more cards this turn, another target creature you control gets +3/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()),
            DrewTwoOrMoreCardsCondition.instance,
            "and gains first strike until end of turn"
        ));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability.addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private LakeTownToymaker(final LakeTownToymaker card) {
        super(card);
    }

    @Override
    public LakeTownToymaker copy() {
        return new LakeTownToymaker(this);
    }
}
