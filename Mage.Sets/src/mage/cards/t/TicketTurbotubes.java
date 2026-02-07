package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.GetTicketCountersControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TicketTurbotubes extends CardImpl {

    public TicketTurbotubes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {3}, {T}: You get {TK}.
        Ability ability = new SimpleActivatedAbility(new GetTicketCountersControllerEffect(1), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TicketTurbotubes(final TicketTurbotubes card) {
        super(card);
    }

    @Override
    public TicketTurbotubes copy() {
        return new TicketTurbotubes(this);
    }
}
