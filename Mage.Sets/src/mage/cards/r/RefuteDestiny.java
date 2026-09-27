package mage.cards.r;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
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
public final class RefuteDestiny extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker that's green or blue");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
        filter.add(Predicates.or(
            new ColorPredicate(ObjectColor.GREEN),
            new ColorPredicate(ObjectColor.BLUE)
        ));
    }

    public RefuteDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target creature or planeswalker that's green or blue. Surveil 1.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private RefuteDestiny(final RefuteDestiny card) {
        super(card);
    }

    @Override
    public RefuteDestiny copy() {
        return new RefuteDestiny(this);
    }
}
