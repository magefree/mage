package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class TerminalCriticism extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker that's blue or red");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
        filter.add(Predicates.or(
            new ColorPredicate(ObjectColor.BLUE),
            new ColorPredicate(ObjectColor.RED)
        ));
    }

    public TerminalCriticism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Destroy target creature or planeswalker that's blue or red. You gain 1 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    private TerminalCriticism(final TerminalCriticism card) {
        super(card);
    }

    @Override
    public TerminalCriticism copy() {
        return new TerminalCriticism(this);
    }
}
