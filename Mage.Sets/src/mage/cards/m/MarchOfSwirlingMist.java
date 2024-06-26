package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.costs.costadjusters.ExileCardsFromHandAdjuster;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchOfSwirlingMist extends CardImpl {

    private static final FilterCard filter = new FilterCard("blue cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public MarchOfSwirlingMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // As an additional cost to cast this spell, you may exile any number of blue cards from your hand. This spell costs {2} less to cast for each card exiled this way.
        ExileCardsFromHandAdjuster.addAdjusterAndMessage(this, filter);

        // Up to X target creatures phase out.
        this.getSpellAbility().addEffect(new PhaseOutTargetEffect().setText("up to X target creatures phase out"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private MarchOfSwirlingMist(final MarchOfSwirlingMist card) {
        super(card);
    }

    @Override
    public MarchOfSwirlingMist copy() {
        return new MarchOfSwirlingMist(this);
    }
}
