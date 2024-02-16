package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishArchivist extends CardImpl {

    public ElvishArchivist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever one or more artifacts enter the battlefield under your control, put two +1/+1 counters on Elvish Archivist. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ).setTriggersOnceEachTurn(true)
                .setTriggerPhrase("Whenever one or more artifacts enter the battlefield under your control, "));

        // Whenever one or more enchantments enter the battlefield under your control, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT
        ).setTriggersOnceEachTurn(true)
                .setTriggerPhrase("Whenever one or more enchantments enter the battlefield under your control, "));
    }

    private ElvishArchivist(final ElvishArchivist card) {
        super(card);
    }

    @Override
    public ElvishArchivist copy() {
        return new ElvishArchivist(this);
    }
}
