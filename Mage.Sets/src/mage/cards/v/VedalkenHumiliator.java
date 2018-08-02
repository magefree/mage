package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAllEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class VedalkenHumiliator extends CardImpl {

    public VedalkenHumiliator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Metalcraft — Whenever Vedalken Humiliator attacks, if you control three or more artifacts, creatures your opponents control lose all abilities and have base power and toughness 1/1 until end of turn.
        TriggeredAbility ability = new AttacksTriggeredAbility(
                new SetPowerToughnessAllEffect(
                        1, 1, Duration.EndOfTurn,
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                        true
                ), false
        );
        ability.addEffect(new LoseAllAbilitiesAllEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                Duration.EndOfTurn
        ));
        this.addAbility(new ConditionalTriggeredAbility(
                ability, MetalcraftCondition.instance,
                "<i>Metalcraft</i> &mdash; Whenever {this} attacks, "
                + "if you control three or more artifacts, "
                + "creatures your opponents control lose all abilities "
                + "and have base power and toughness 1/1 until end of turn."
        ));
    }

    public VedalkenHumiliator(final VedalkenHumiliator card) {
        super(card);
    }

    @Override
    public VedalkenHumiliator copy() {
        return new VedalkenHumiliator(this);
    }
}
