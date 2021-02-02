package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

/**
 *
 * @author TheElk801
 */
public final class DragonsHoard extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "a Dragon");

    public DragonsHoard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a Dragon enters the battlefield under your control, put a gold counter on Dragon's Hoard.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.GOLD.createInstance()), filter
        ));

        // {T}, Remove a gold counter from Dragon's Hoard: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapSourceCost()
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.GOLD.createInstance()));
        this.addAbility(ability);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private DragonsHoard(final DragonsHoard card) {
        super(card);
    }

    @Override
    public DragonsHoard copy() {
        return new DragonsHoard(this);
    }
}
