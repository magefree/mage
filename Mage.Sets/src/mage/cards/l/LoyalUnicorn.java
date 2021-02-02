package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class LoyalUnicorn extends CardImpl {

    public LoyalUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lieutenant — At the beginning of combat on your turn, if you control your commander, prevent all combat damage that would be dealt to creatures you control this turn. Other creatures you control gain vigilance until end of turn.
        TriggeredAbility ability = new BeginningOfCombatTriggeredAbility(
                new PreventAllDamageToAllEffect(
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURES,
                        true
                ), TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES, true
        ));
        this.addAbility(new ConditionalTriggeredAbility(
                ability, CommanderInPlayCondition.instance,
                "<i>Lieutenant</i> &mdash; At the beginning of combat "
                + "on your turn, if you control your commander, "
                + "prevent all combat damage that would be dealt "
                + "to creatures you control this turn. "
                + "Other creatures you control gain vigilance until end of turn."
        ));
    }

    private LoyalUnicorn(final LoyalUnicorn card) {
        super(card);
    }

    @Override
    public LoyalUnicorn copy() {
        return new LoyalUnicorn(this);
    }
}
