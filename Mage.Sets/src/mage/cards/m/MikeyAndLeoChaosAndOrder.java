package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MikeyAndLeoChaosAndOrder extends CardImpl {

    public MikeyAndLeoChaosAndOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/W}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you put a counter on a creature you control, draw a card. This ability triggers only once each turn.
        this.addAbility(new PutCounterOnPermanentTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                null, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setTriggersLimitEachTurn(1).setTriggerPhrase("Whenever you put a counter on a creature you control, "));
    }

    private MikeyAndLeoChaosAndOrder(final MikeyAndLeoChaosAndOrder card) {
        super(card);
    }

    @Override
    public MikeyAndLeoChaosAndOrder copy() {
        return new MikeyAndLeoChaosAndOrder(this);
    }
}
