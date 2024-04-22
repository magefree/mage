package mage.cards.d;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.InklingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DramaticFinale extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("one or more nontoken creatures you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public DramaticFinale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W/B}{W/B}{W/B}{W/B}");

        // Creature tokens you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));

        // Whenever one or more nontoken creatures you control die, create a 2/1 white and black Inkling creature token with flying. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new InklingToken()), false, filter
        ).setTriggersOnceEachTurn(true));
    }

    private DramaticFinale(final DramaticFinale card) {
        super(card);
    }

    @Override
    public DramaticFinale copy() {
        return new DramaticFinale(this);
    }
}
