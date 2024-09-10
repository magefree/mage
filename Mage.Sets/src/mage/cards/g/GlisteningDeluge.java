package mage.cards.g;

import mage.ObjectColor;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlisteningDeluge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE)
        ));
    }

    public GlisteningDeluge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // All creatures get -1/-1 until end of turn. Creatures that are green and/or white get an additional -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn
        ).setText("all creatures get -1/-1 until end of turn"));
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, filter, false
        ).setText("creatures that are green and/or white get an additional -2/-2 until end of turn"));
    }

    private GlisteningDeluge(final GlisteningDeluge card) {
        super(card);
    }

    @Override
    public GlisteningDeluge copy() {
        return new GlisteningDeluge(this);
    }
}
