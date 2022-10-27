package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AjanisInfluence extends CardImpl {

    private static final FilterCard filter = new FilterCard("a white card");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AjanisInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Put two +1/+1 counters on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Look at the top five cards of your library. You may reveal a white card form among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM).concatBy("<br>"));
    }

    private AjanisInfluence(final AjanisInfluence card) {
        super(card);
    }

    @Override
    public AjanisInfluence copy() {
        return new AjanisInfluence(this);
    }
}
