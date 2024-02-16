package mage.cards.b;

import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreathWeapon extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Dragon creature");

    static {
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public BreathWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Breath Weapon deals 2 damage to each non-Dragon creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
    }

    private BreathWeapon(final BreathWeapon card) {
        super(card);
    }

    @Override
    public BreathWeapon copy() {
        return new BreathWeapon(this);
    }
}
