package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BreakTheSpell extends CardImpl {

    public BreakTheSpell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Destroy target enchantment. If a permanent you controlled or a token was destroyed this way, draw a card.
        this.getSpellAbility().addEffect(new BreakTheSpellEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
    }

    private BreakTheSpell(final BreakTheSpell card) {
        super(card);
    }

    @Override
    public BreakTheSpell copy() {
        return new BreakTheSpell(this);
    }
}

class BreakTheSpellEffect extends OneShotEffect {

    BreakTheSpellEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target enchantment. If a permanent you controlled or "
                + "a token was destroyed this way, draw a card.";
    }

    private BreakTheSpellEffect(final BreakTheSpellEffect effect) {
        super(effect);
    }

    @Override
    public BreakTheSpellEffect copy() {
        return new BreakTheSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        boolean followupEffect = permanent.isControlledBy(source.getControllerId())
                || StaticFilters.FILTER_PERMANENT_TOKEN.match(permanent, game);
        boolean destroyed = permanent.destroy(source, game, false);
        game.getState().processAction(game);

        if (followupEffect && destroyed) {
            new DrawCardSourceControllerEffect(1).apply(game, source);
        }

        return true;
    }
}
