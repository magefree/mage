package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FractalToken;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SequenceEngine extends CardImpl {

    public SequenceEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        // {X}, {T}: Exile target creature card with mana value X from a graveyard. Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileTargetEffect()
                        .setText("exile target creature card with mana value X from a graveyard."),
                new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(FractalToken.getEffect(
                ManacostVariableValue.REGULAR, "Put X +1/+1 counters on it"
        ));
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.addAbility(ability);
    }

    private SequenceEngine(final SequenceEngine card) {
        super(card);
    }

    @Override
    public SequenceEngine copy() {
        return new SequenceEngine(this);
    }
}
