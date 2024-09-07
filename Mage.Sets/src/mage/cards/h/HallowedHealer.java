package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author cbt33
 */

public final class HallowedHealer extends CardImpl {

    public HallowedHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Threshold - {tap}: Prevent the next 4 damage that would be dealt to any target this turn. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ConditionalActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 4),
                new TapSourceCost(), ThresholdCondition.instance
        );
        thresholdAbility.addTarget(new TargetAnyTarget());
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private HallowedHealer(final HallowedHealer card) {
        super(card);
    }

    @Override
    public HallowedHealer copy() {
        return new HallowedHealer(this);
    }
}
