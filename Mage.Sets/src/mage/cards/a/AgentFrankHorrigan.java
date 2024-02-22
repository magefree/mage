package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentFrankHorrigan extends CardImpl {

    public AgentFrankHorrigan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Agent Frank Horrigan has indestructible as long as it attacked this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                AttackedThisTurnSourceCondition.instance, "{this} has indestructible as long as it attacked this turn"
        )));

        // Whenever Agent Frank Horrigan enters the battlefield or attacks, proliferate twice.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ProliferateEffect(false));
        ability.addEffect(new ProliferateEffect().setText("twice"));
        this.addAbility(ability);
    }

    private AgentFrankHorrigan(final AgentFrankHorrigan card) {
        super(card);
    }

    @Override
    public AgentFrankHorrigan copy() {
        return new AgentFrankHorrigan(this);
    }
}
