package mage.cards.n;

import java.util.UUID;

import mage.abilities.common.CombatDamageDealtToYouTriggeredAbility;
import mage.abilities.common.PlayerAttacksTriggeredAbility;
import mage.abilities.condition.common.AttackedPlayersPoisonedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author alexander-novo
 */
public final class NornsDecree extends CardImpl {

    public NornsDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ENCHANTMENT }, "{2}{W}");

        // Whenever one or more creatures an opponent controls deal combat damage to you, that opponent gets a poison counter.
        this.addAbility(new CombatDamageDealtToYouTriggeredAbility(new AddPoisonCounterTargetEffect(1).setText("that opponent gets a poison counter."), true));

        // Whenever a player attacks, if one or more players being attacked are poisoned, the attacking player draws a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new PlayerAttacksTriggeredAbility(new DrawCardTargetEffect(1), true),
                AttackedPlayersPoisonedCondition.instance,
                "Whenever a player attacks, if one or more players being attacked are poisoned, the attacking player draws a card."));
    }

    private NornsDecree(final NornsDecree card) {
        super(card);
    }

    @Override
    public NornsDecree copy() {
        return new NornsDecree(this);
    }

}
