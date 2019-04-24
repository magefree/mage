package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author TheElk801
 */
public final class OpenTheGraves extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public OpenTheGraves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever a nontoken creature you control dies, create a 2/2 black Zombie creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()),
                false, filter
        ));
    }

    public OpenTheGraves(final OpenTheGraves card) {
        super(card);
    }

    @Override
    public OpenTheGraves copy() {
        return new OpenTheGraves(this);
    }
}
