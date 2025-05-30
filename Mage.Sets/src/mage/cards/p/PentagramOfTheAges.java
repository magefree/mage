
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class PentagramOfTheAges extends CardImpl {

    public PentagramOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {4}, {T}: The next time a source of your choice would deal damage to you this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, true), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private PentagramOfTheAges(final PentagramOfTheAges card) {
        super(card);
    }

    @Override
    public PentagramOfTheAges copy() {
        return new PentagramOfTheAges(this);
    }
}
