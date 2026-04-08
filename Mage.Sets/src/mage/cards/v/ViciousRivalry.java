package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class ViciousRivalry extends CardImpl {

    public ViciousRivalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");

        // As an additional cost to cast this spell, pay X life.
        this.getSpellAbility().addCost(new PayVariableLifeCost(true));

        // Destroy all artifacts and creatures with mana value X or less.
        this.getSpellAbility().addEffect(new ViciousRivalryEffect());
    }

    private ViciousRivalry(final ViciousRivalry card) {
        super(card);
    }

    @Override
    public ViciousRivalry copy() {
        return new ViciousRivalry(this);
    }
}

class ViciousRivalryEffect extends OneShotEffect {

    ViciousRivalryEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all artifacts and creatures with mana value X or less";
    }

    private ViciousRivalryEffect(final ViciousRivalryEffect effect) {
        super(effect);
    }

    @Override
    public ViciousRivalryEffect copy() {
        return new ViciousRivalryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE,
                source.getControllerId(),
                source, game)) {
            if (permanent.getManaValue() <= CardUtil.getSourceCostsTag(game, source, "X", 0)) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
