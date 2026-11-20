package mage.cards.t;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class TemporalTribulation extends CardImpl {

    public TemporalTribulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Exile two target permanents you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, StaticFilters.FILTER_CONTROLLED_PERMANENTS));

        // Flashback {5}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{U}")));
    }

    private TemporalTribulation(final TemporalTribulation card) {
        super(card);
    }

    @Override
    public TemporalTribulation copy() {
        return new TemporalTribulation(this);
    }
}
