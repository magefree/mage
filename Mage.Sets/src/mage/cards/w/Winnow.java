package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Winnow extends CardImpl {

    public Winnow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Destroy target nonland permanent if another permanent with the same name is on the battlefield.
        this.getSpellAbility().addEffect(new WinnowEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Winnow(final Winnow card) {
        super(card);
    }

    @Override
    public Winnow copy() {
        return new Winnow(this);
    }
}

class WinnowEffect extends DestroyTargetEffect {

    WinnowEffect() {
        super();
        staticText = "Destroy target nonland permanent if another permanent with the same name is on the battlefield";
    }

    private WinnowEffect(final WinnowEffect effect) {
        super(effect);
    }

    @Override
    public WinnowEffect copy() {
        return new WinnowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target == null) {
            return false;
        }
        return game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT, source.getControllerId(), source, game)
                .stream()
                .filter(p -> p.sharesName(target, game))
                .anyMatch(p -> !p.getId().equals(target.getId()))
                && target.destroy(source, game);
    }
}
