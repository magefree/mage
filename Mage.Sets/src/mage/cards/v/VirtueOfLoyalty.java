package mage.cards.v;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VirtueOfLoyalty extends AdventureCard {

    public VirtueOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new CardType[]{CardType.INSTANT}, "{3}{W}{W}", "Ardenvale Fealty", "{1}{W}");

        // At the beginning of your end step, put a +1/+1 counter on each creature you control. Untap those creatures.
        TriggeredAbility trigger = new BeginningOfYourEndStepTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
                false
        );
        trigger.addEffect(new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE).setText("untap those creatures"));
        this.addAbility(trigger);

        // Ardenvale Fealty
        // Create a 2/2 white Knight creature token with vigilance.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken()));

        this.finalizeAdventure();
    }

    private VirtueOfLoyalty(final VirtueOfLoyalty card) {
        super(card);
    }

    @Override
    public VirtueOfLoyalty copy() {
        return new VirtueOfLoyalty(this);
    }
}
