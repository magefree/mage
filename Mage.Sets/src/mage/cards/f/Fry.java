package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Fry extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker that's white or blue");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)
        ));
    }

    public Fry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Fry deals 5 damage to target creature or planeswalker that's white or blue.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Fry(final Fry card) {
        super(card);
    }

    @Override
    public Fry copy() {
        return new Fry(this);
    }
}
