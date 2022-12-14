package mage.cards.d;

import mage.abilities.effects.common.continuous.GainControlAndUntapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

public class DyadForceTransfer extends CardImpl {
    public DyadForceTransfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        //Gain control of target noncreature permanent until end of turn. Untap it.
        this.getSpellAbility().addEffect(new GainControlAndUntapTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPermanent(1, 1,
                StaticFilters.FILTER_PERMANENT_NON_CREATURE));

        //Scry 3.
        this.getSpellAbility().addEffect(new ScryEffect(3, true));
    }

    public DyadForceTransfer(DyadForceTransfer card) {
        super(card);
    }

    @Override
    public DyadForceTransfer copy() {
        return new DyadForceTransfer(this);
    }
}
