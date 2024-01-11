package mage.cards.l;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class LifeFindsAWay extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature with power 4 or greater");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public LifeFindsAWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a nontoken creature with power 4 or greater enters the battlefield under your control, populate.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new PopulateEffect(), filter));
    }

    private LifeFindsAWay(final LifeFindsAWay card) {
        super(card);
    }

    @Override
    public LifeFindsAWay copy() {
        return new LifeFindsAWay(this);
    }
}
