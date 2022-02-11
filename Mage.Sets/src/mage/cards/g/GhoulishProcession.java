package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieDecayedToken;

/**
 *
 * @author weirddan455
 */
public final class GhoulishProcession extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("one or more nontoken creatures");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public GhoulishProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever one or more nontoken creatures die, create a 2/2 black Zombie creature token with decayed. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new ZombieDecayedToken()), false, filter).setTriggersOnce(true));
    }

    private GhoulishProcession(final GhoulishProcession card) {
        super(card);
    }

    @Override
    public GhoulishProcession copy() {
        return new GhoulishProcession(this);
    }
}
