
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class GrixisCharm extends CardImpl {

    public GrixisCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}{R}");

        // Choose one - Return target permanent to its owner's hand;
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        // or target creature gets -4/-4 until end of turn;
        Mode mode = new Mode(new BoostTargetEffect(-4, -4, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or creatures you control get +2/+0 until end of turn.
        mode = new Mode(new BoostControlledEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false));
        this.getSpellAbility().addMode(mode);
    }

    private GrixisCharm(final GrixisCharm card) {
        super(card);
    }

    @Override
    public GrixisCharm copy() {
        return new GrixisCharm(this);
    }
}
