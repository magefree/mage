package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.PhyrexianGolemToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IncubationSac extends CardImpl {

    public IncubationSac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        // Incubation Sac enters the battlefield with three oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(3)),
                "with three oil counters on it"
        ));

        // {4}, {T}, Remove an oil counter from Incubation Sac: Create a 3/3 colorless Phyrexian Golem artifact creature token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new PhyrexianGolemToken()), new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance()));
        this.addAbility(ability);
    }

    private IncubationSac(final IncubationSac card) {
        super(card);
    }

    @Override
    public IncubationSac copy() {
        return new IncubationSac(this);
    }
}
