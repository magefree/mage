package mage.cards.c;

import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleverConcealment extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("nonland permanents you control");
    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public CleverConcealment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Any number of target nonland permanents you control phase out.
        this.getSpellAbility().addEffect(new PhaseOutTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter));
    }

    private CleverConcealment(final CleverConcealment card) {
        super(card);
    }

    @Override
    public CleverConcealment copy() {
        return new CleverConcealment(this);
    }
}
