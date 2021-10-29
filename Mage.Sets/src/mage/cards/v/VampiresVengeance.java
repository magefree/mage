package mage.cards.v;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VampiresVengeance extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Vampire creature.");

    static {
        filter.add(Predicates.not(SubType.VAMPIRE.getPredicate()));
    }

    public VampiresVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Vampires' Vengeance deals 2 damage to each non-Vampire creature. Create a Blood token.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BloodToken()));
    }

    private VampiresVengeance(final VampiresVengeance card) {
        super(card);
    }

    @Override
    public VampiresVengeance copy() {
        return new VampiresVengeance(this);
    }
}
