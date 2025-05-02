package mage.cards.t;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TradeTheHelm extends CardImpl {

    public TradeTheHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Exchange control of target artifact or creature you control and target artifact or creature an opponent controls.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(
                Duration.EndOfGame, "exchange control of target artifact or creature you control " +
                "and target artifact or creature an opponent controls", false, true
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private TradeTheHelm(final TradeTheHelm card) {
        super(card);
    }

    @Override
    public TradeTheHelm copy() {
        return new TradeTheHelm(this);
    }
}
