package mage.cards.e;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class EssenceBurn extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("black or green creature or planeswalker");

    static {
        filter.add(Predicates.or(
            new ColorPredicate(ObjectColor.BLACK),
            new ColorPredicate(ObjectColor.GREEN)
        ));
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
    }

    public EssenceBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Essence Burn deals 5 damage to target black or green creature or planeswalker. If that permanent would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect("permanent"));
    }

    private EssenceBurn(final EssenceBurn card) {
        super(card);
    }

    @Override
    public EssenceBurn copy() {
        return new EssenceBurn(this);
    }
}
