package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReignOfTerror extends CardImpl {

    public ReignOfTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Destroy all green creatures or all white creatures. They can't be regenerated. You lose 2 life for each creature that died this way.
        this.getSpellAbility().addEffect(new ReignOfTerrorEffect());
    }

    private ReignOfTerror(final ReignOfTerror card) {
        super(card);
    }

    @Override
    public ReignOfTerror copy() {
        return new ReignOfTerror(this);
    }
}

class ReignOfTerrorEffect extends OneShotEffect {

    private static final FilterPermanent greenFilter = new FilterCreaturePermanent();
    private static final FilterPermanent whiteFilter = new FilterCreaturePermanent();

    static {
        greenFilter.add(new ColorPredicate(ObjectColor.GREEN));
        whiteFilter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    ReignOfTerrorEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy all green creatures or all white creatures. They can't be regenerated. " +
                "You lose 2 life for each creature that died this way.";
    }

    private ReignOfTerrorEffect(final ReignOfTerrorEffect effect) {
        super(effect);
    }

    @Override
    public ReignOfTerrorEffect copy() {
        return new ReignOfTerrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = player.chooseUse(
                outcome, "Destroy all green creatures or all white creatures?",
                "", "Green", "White", source, game
        ) ? greenFilter : whiteFilter;
        int died = game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .mapToInt(permanent -> permanent.destroy(source, game, true) ? 1 : 0)
                .sum();
        if (died > 0) {
            game.getState().processAction(game);
            player.loseLife(2 * died, game, source, false);
            return true;
        }
        return false;
    }
}
