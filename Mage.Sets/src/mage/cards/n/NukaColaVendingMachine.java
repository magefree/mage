package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NukaColaVendingMachine extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FOOD, "a Food");

    public NukaColaVendingMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {1}, {T}: Create a Food token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new FoodToken()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever you sacrifice a Food, create a tapped Treasure token.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true), filter
        ));
    }

    private NukaColaVendingMachine(final NukaColaVendingMachine card) {
        super(card);
    }

    @Override
    public NukaColaVendingMachine copy() {
        return new NukaColaVendingMachine(this);
    }
}
