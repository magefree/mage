package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitchsOven extends CardImpl {

    public WitchsOven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}, Sacrifice a creature: Create a Food token. If the sacrificed creature's toughness was 4 or greater, create two Food tokens instead.
        Ability ability = new SimpleActivatedAbility(new WitchsOvenEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));
        this.addAbility(ability);
    }

    private WitchsOven(final WitchsOven card) {
        super(card);
    }

    @Override
    public WitchsOven copy() {
        return new WitchsOven(this);
    }
}

class WitchsOvenEffect extends OneShotEffect {

    private static final Effect effect1 = new CreateTokenEffect(new FoodToken(), 1);
    private static final Effect effect2 = new CreateTokenEffect(new FoodToken(), 2);

    WitchsOvenEffect() {
        super(Outcome.Benefit);
        staticText = "Create a Food token. If the sacrificed creature's toughness " +
                "was 4 or greater, create two Food tokens instead";
    }

    private WitchsOvenEffect(final WitchsOvenEffect effect) {
        super(effect);
    }

    @Override
    public WitchsOvenEffect copy() {
        return new WitchsOvenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean big = source
                .getCosts()
                .stream()
                .filter(SacrificeTargetCost.class::isInstance)
                .map(SacrificeTargetCost.class::cast)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .map(Permanent::getToughness)
                .mapToInt(MageInt::getValue)
                .anyMatch(i -> i > 3);
        if (big) {
            return effect2.apply(game, source);
        }
        return effect1.apply(game, source);
    }
}
