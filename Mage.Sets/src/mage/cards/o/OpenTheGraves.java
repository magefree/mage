package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author TheElk801
 */
public final class OpenTheGraves extends CardImpl {

    public OpenTheGraves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever a nontoken creature you control dies, create a 2/2 black Zombie creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()),
                false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ));
    }

    private OpenTheGraves(final OpenTheGraves card) {
        super(card);
    }

    @Override
    public OpenTheGraves copy() {
        return new OpenTheGraves(this);
    }
}
