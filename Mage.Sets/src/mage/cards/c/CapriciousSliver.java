package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CapriciousSliver extends CardImpl {

    public CapriciousSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control have "Whenever this creature deals combat damage to a player, exile the top card of your library. You may play that card this turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new ExileTopXMayPlayUntilEndOfTurnEffect(1), false
                ).setTriggerPhrase("Whenever this creature deals combat damage to a player, "),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private CapriciousSliver(final CapriciousSliver card) {
        super(card);
    }

    @Override
    public CapriciousSliver copy() {
        return new CapriciousSliver(this);
    }
}
