package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KlingonWarriorToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class GloryToTheEmpire extends CardImpl {

    public GloryToTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // When this enchantment enters, create a number of 2/1 red Klingon Warrior creature tokens with haste equal to the number of opponents you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new CreateTokenEffect(new KlingonWarriorToken(), OpponentsCount.instance)
                .setText("create a number of 2/1 red Klingon Warrior creature tokens with haste "
                    + "equal to the number of opponents you have"
            )
        ));

        // Creatures you control have melee.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new MeleeAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
        )));
    }

    private GloryToTheEmpire(final GloryToTheEmpire card) {
        super(card);
    }

    @Override
    public GloryToTheEmpire copy() {
        return new GloryToTheEmpire(this);
    }
}
