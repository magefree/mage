package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TurbulentDreams extends CardImpl {

    public TurbulentDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}");

        // As an additional cost to cast Turbulent Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(StaticFilters.FILTER_CARD_CARDS, true));

        // Return X target nonland permanents to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target nonland permanents to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(TurbulentDreamsAdjuster.instance);
    }

    private TurbulentDreams(final TurbulentDreams card) {
        super(card);
    }

    @Override
    public TurbulentDreams copy() {
        return new TurbulentDreams(this);
    }
}

enum TurbulentDreamsAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanents");

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Target target = new TargetPermanent(ability.getCosts().getVariableCosts().get(0).getAmount(), filter);
        ability.addTarget(target);
    }
}