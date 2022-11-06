package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheStasisCoffin extends CardImpl {

    public TheStasisCoffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.addSuperType(SuperType.LEGENDARY);

        // {2}, {T}, Exile The Stasis Coffin: You gain protection from everything until your next turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControllerEffect(
                new ProtectionFromEverythingAbility(), Duration.UntilYourNextTurn
        ), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private TheStasisCoffin(final TheStasisCoffin card) {
        super(card);
    }

    @Override
    public TheStasisCoffin copy() {
        return new TheStasisCoffin(this);
    }
}
