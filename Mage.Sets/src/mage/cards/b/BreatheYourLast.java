package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BreatheYourLast extends CardImpl {

    public BreatheYourLast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Destroy target creature or planeswalker. You gain 1 life for each of its colors.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new BreatheYourLastEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private BreatheYourLast(final BreatheYourLast card) {
        super(card);
    }

    @Override
    public BreatheYourLast copy() {
        return new BreatheYourLast(this);
    }
}

class BreatheYourLastEffect extends OneShotEffect {

    BreatheYourLastEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "You gain 1 life for each of its colors.";
    }

    private BreatheYourLastEffect(final BreatheYourLastEffect effect) {
        super(effect);
    }

    @Override
    public BreatheYourLastEffect copy() {
        return new BreatheYourLastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null || controller == null) {
            return false;
        }
        int colors = permanent.getColor(game).getColorCount();
        return controller.gainLife(colors, game, source) > 0;
    }
}