package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TreasureSpentToCastCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadedSellSword extends CardImpl {

    public JadedSellSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Jaded Sell-Sword enters the battlefield, if mana from a Treasure was spent to cast it, it gains first strike and haste until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainAbilitySourceEffect(
                        FirstStrikeAbility.getInstance(), Duration.EndOfTurn
                )), TreasureSpentToCastCondition.instance, "When {this} enters the battlefield, " +
                "if mana from a Treasure was spent to cast it, it gains first strike and haste until end of turn."
        );
        ability.addEffect(new GainAbilitySourceEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ));
        this.addAbility(ability);
    }

    private JadedSellSword(final JadedSellSword card) {
        super(card);
    }

    @Override
    public JadedSellSword copy() {
        return new JadedSellSword(this);
    }
}
