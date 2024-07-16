package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FishNoAbilityToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Fountainport extends CardImpl {

    public Fountainport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Sacrifice a token: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_TOKEN));
        this.addAbility(ability);

        // {3}, {T}, Pay 1 life: Create a 1/1 blue Fish creature token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new FishNoAbilityToken()), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {4}, {T}: Create a Treasure token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Fountainport(final Fountainport card) {
        super(card);
    }

    @Override
    public Fountainport copy() {
        return new Fountainport(this);
    }
}
