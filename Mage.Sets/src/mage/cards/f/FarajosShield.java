package mage.cards.f;

import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;

import java.util.UUID;

/**
 *
 * @author EikePeace
 */
public final class FarajosShield extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by creatures that are enchanted, equipped or have counters on them");

    static {
        filter.add(Predicates.not(new EnchantedPredicate()));
        filter.add(Predicates.not(new CounterAnyPredicate()));
        filter.add(Predicates.not(new EquippedPredicate()));
    }

    public FarajosShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, false));

    }
   public FarajosShield(final FarajosShield card) {
            super(card);
        }

        @Override
        public FarajosShield copy() {
            return new FarajosShield(this);
        }
}
