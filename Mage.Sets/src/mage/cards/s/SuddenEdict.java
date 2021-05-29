package mage.cards.s;

import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuddenEdict extends CardImpl {

    public SuddenEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Target player sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SuddenEdict(final SuddenEdict card) {
        super(card);
    }

    @Override
    public SuddenEdict copy() {
        return new SuddenEdict(this);
    }
}
