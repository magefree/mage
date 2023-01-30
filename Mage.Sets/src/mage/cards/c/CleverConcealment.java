package mage.cards.c;

import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleverConcealment extends CardImpl {

    public CleverConcealment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Any number of target nonland permanents you control phase out.
        this.getSpellAbility().addEffect(new PhaseOutTargetEffect("any number of target nonland permanents you control"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND));
    }

    private CleverConcealment(final CleverConcealment card) {
        super(card);
    }

    @Override
    public CleverConcealment copy() {
        return new CleverConcealment(this);
    }
}
