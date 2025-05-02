package mage.cards.s;

import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.RevealPutInHandLoseLifeEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class StrongholdArena extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(MultikickerCount.instance, 3);

    public StrongholdArena(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Kicker {G} and/or {W}
        KickerAbility kickerAbility = new KickerAbility("{G}");
        kickerAbility.addKickerCost("{W}");
        this.addAbility(kickerAbility);

        // When Stronghold Arena enters the battlefield, you gain 3 life for each time it was kicked.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(
                xValue, "you gain 3 life for each time it was kicked"
        )));

        // Whenever one or more creatures you control deal combat damage to a player, you may reveal the top card of your library and put it into your hand.
        // If you do, you lose life equal to its mana value.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                Zone.BATTLEFIELD, new RevealPutInHandLoseLifeEffect(true),
                StaticFilters.FILTER_PERMANENT_CREATURES,
                SetTargetPointer.NONE, true
        ));
    }

    private StrongholdArena(final StrongholdArena card) {
        super(card);
    }

    @Override
    public StrongholdArena copy() {
        return new StrongholdArena(this);
    }
}
