package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GremlinToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraGremlinWrangler extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.GREMLIN.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public ChandraGremlinWrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(3);

        // +1: Create a 2/2 red Gremlin creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new GremlinToken()), 1));

        // -2: Chandra, Gremlin Wrangler deals X damage to any target, where X is the number of Gremlins you control.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(xValue).setText("{this} deals X damage to any target, where X is the number of Gremlins you control."), -2);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ChandraGremlinWrangler(final ChandraGremlinWrangler card) {
        super(card);
    }

    @Override
    public ChandraGremlinWrangler copy() {
        return new ChandraGremlinWrangler(this);
    }
}
