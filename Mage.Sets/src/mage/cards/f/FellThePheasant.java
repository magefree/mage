package mage.cards.f;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FellThePheasant extends CardImpl {

    public FellThePheasant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Fell the Pheasant deals 5 damage to target creature with flying. Create a Food token.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private FellThePheasant(final FellThePheasant card) {
        super(card);
    }

    @Override
    public FellThePheasant copy() {
        return new FellThePheasant(this);
    }
}
