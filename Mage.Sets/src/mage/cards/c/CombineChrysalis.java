package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BeastToken2;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CombineChrysalis extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public CombineChrysalis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}{U}");

        // Creature tokens you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));

        // {2}{G}{U}, {T}, Sacrifice a token: Create a 4/4 green Beast creature token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new BeastToken2()), new ManaCostsImpl<>("{2}{G}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private CombineChrysalis(final CombineChrysalis card) {
        super(card);
    }

    @Override
    public CombineChrysalis copy() {
        return new CombineChrysalis(this);
    }
}
