package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RatsFeast extends CardImpl {

    public RatsFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Exile X target cards from a single graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect("Exile X target cards from a single graveyard"));
        this.getSpellAbility().setTargetAdjuster(RatsFeastAdjuster.instance);
    }

    private RatsFeast(final RatsFeast card) {
        super(card);
    }

    @Override
    public RatsFeast copy() {
        return new RatsFeast(this);
    }
}

enum RatsFeastAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInASingleGraveyard(xValue, xValue, StaticFilters.FILTER_CARD_CARDS));
    }
}
