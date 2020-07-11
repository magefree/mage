package mage.cards.f;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FellThePheasant extends CardImpl {


    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public FellThePheasant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Fell the Pheasant deals 5 damage to target creature with flying. Create a Food token.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private FellThePheasant(final FellThePheasant card) {
        super(card);
    }

    @Override
    public FellThePheasant copy() {
        return new FellThePheasant(this);
    }
}
