package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GravbladeHeavy extends CardImpl {

    private static final Condition condition =
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT);

    public GravbladeHeavy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As long as you control an artifact, this creature gets +1/+0 and has deathtouch.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                condition, "as long as you control an artifact, {this} gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield
                ), condition, "and has deathtouch"
        ));
        this.addAbility(ability);
    }

    private GravbladeHeavy(final GravbladeHeavy card) {
        super(card);
    }

    @Override
    public GravbladeHeavy copy() {
        return new GravbladeHeavy(this);
    }
}
