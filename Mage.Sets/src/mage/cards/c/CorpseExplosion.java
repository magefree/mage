package mage.cards.c;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class CorpseExplosion extends CardImpl {

    public CorpseExplosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{R}");

        // As an additional cost to cast this spell, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));

        // Corpse Explosion deals damage equal to the exiled card's power to each creature and each planeswalker.
        this.getSpellAbility().addEffect(new CorpseExplosionEffect());
    }

    private CorpseExplosion(final CorpseExplosion card) {
        super(card);
    }

    @Override
    public CorpseExplosion copy() {
        return new CorpseExplosion(this);
    }
}

class CorpseExplosionEffect extends OneShotEffect {

    public CorpseExplosionEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage equal to the exiled card's power to each creature and each planeswalker";
    }

    private CorpseExplosionEffect(final CorpseExplosionEffect effect) {
        super(effect);
    }

    @Override
    public CorpseExplosionEffect copy() {
        return new CorpseExplosionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                List<Card> exiledCards = ((ExileFromGraveCost) cost).getExiledCards();
                if (!exiledCards.isEmpty()) {
                    power = exiledCards.get(0).getPower().getValue();
                    break;
                }
            }
        }
        if (power < 1) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER, source.getControllerId(), source, game)) {
            permanent.damage(power, source, game);
        }
        return true;
    }
}
