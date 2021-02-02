package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class LeoninVanguard extends CardImpl {

    public LeoninVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control three or more creatures, Leonin Vanguard gets +1/+1 until end of turn and you gain 1 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                        TargetController.YOU, false
                ),
                new PermanentsOnTheBattlefieldCondition(
                        StaticFilters.FILTER_CONTROLLED_CREATURES,
                        ComparisonType.MORE_THAN, 2
                ),
                "At the beginning of combat on your turn, "
                + "if you control three or more creatures, "
                + "{this} gets +1/+1 until end of turn and you gain 1 life."
        );
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);
    }

    private LeoninVanguard(final LeoninVanguard card) {
        super(card);
    }

    @Override
    public LeoninVanguard copy() {
        return new LeoninVanguard(this);
    }
}
