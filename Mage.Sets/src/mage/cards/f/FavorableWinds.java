package mage.cards.f;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public final class FavorableWinds extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public FavorableWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // Creatures you control with flying get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));
    }

    private FavorableWinds(final FavorableWinds card) {
        super(card);
    }

    @Override
    public FavorableWinds copy() {
        return new FavorableWinds(this);
    }
}
