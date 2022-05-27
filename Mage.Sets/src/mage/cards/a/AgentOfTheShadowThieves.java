package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOpponentWithMostLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentOfTheShadowThieves extends CardImpl {

    public AgentOfTheShadowThieves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever this creature attacks a player, if no opponent has more life than that player, put a +1/+1 counter on this creature. It gains deathtouch and indestructible until end of turn."
        Ability ability = new AttacksOpponentWithMostLifeTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on this creature"),
                false
        );
        ability.addEffect(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains deathtouch"));
        ability.addEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and indestructible until end of turn"));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private AgentOfTheShadowThieves(final AgentOfTheShadowThieves card) {
        super(card);
    }

    @Override
    public AgentOfTheShadowThieves copy() {
        return new AgentOfTheShadowThieves(this);
    }
}
