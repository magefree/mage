package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinsAutomaton extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPlaneswalkerPermanent(SubType.DOVIN));

    public DovinsAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as you control a Dovin planeswalker, Dovin's Automaton gets +2/+2 and has vigilance.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                        condition, "As long as you control a Dovin planeswalker, {this} gets +2/+2"
                )
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        VigilanceAbility.getInstance(), Duration.WhileOnBattlefield
                ), condition, "and has vigilance"
        ));
        this.addAbility(ability);
    }

    private DovinsAutomaton(final DovinsAutomaton card) {
        super(card);
    }

    @Override
    public DovinsAutomaton copy() {
        return new DovinsAutomaton(this);
    }
}
