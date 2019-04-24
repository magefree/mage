
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author Quercitron
 */
public final class Overburden extends CardImpl {

    private static final FilterCreaturePermanent ENTERS_BATTLEFIELD_FILTER = new FilterCreaturePermanent("a nontoken creature");

    private static final FilterControlledLandPermanent RETURN_FILTER = new FilterControlledLandPermanent("a land");

    static {
        ENTERS_BATTLEFIELD_FILTER.add(Predicates.not(new TokenPredicate()));
    }

    public Overburden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever a player puts a nontoken creature onto the battlefield, that player returns a land he or she controls to its owner's hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new ReturnToHandChosenPermanentEffect(RETURN_FILTER),
                ENTERS_BATTLEFIELD_FILTER,
                false,
                SetTargetPointer.PLAYER,
                "Whenever a player puts a nontoken creature onto the battlefield,"
                + " that player returns a land he or she controls to its owner's hand."));
    }

    public Overburden(final Overburden card) {
        super(card);
    }

    @Override
    public Overburden copy() {
        return new Overburden(this);
    }
}
