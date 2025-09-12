package mage.cards.i;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntimidationTactics extends CardImpl {

    public IntimidationTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target opponent reveals their hand. You choose an artifact or creature card from it. Exile that card.
        this.getSpellAbility().addEffect(new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}")));
    }

    private IntimidationTactics(final IntimidationTactics card) {
        super(card);
    }

    @Override
    public IntimidationTactics copy() {
        return new IntimidationTactics(this);
    }
}
