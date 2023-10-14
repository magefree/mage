package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noahg
 */
public final class SearingRays extends CardImpl {

    public SearingRays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");


        // Choose a color. Searing Rays deals damage to each player equal to the number of creatures of that color that player controls.
        this.getSpellAbility().addEffect(new SearingRaysEffect());
    }

    private SearingRays(final SearingRays card) {
        super(card);
    }

    @Override
    public SearingRays copy() {
        return new SearingRays(this);
    }
}

class SearingRaysEffect extends OneShotEffect {

    public SearingRaysEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose a color. {this} deals damage to each player equal to the number of creatures of that color that player controls";
    }

    private SearingRaysEffect(final SearingRaysEffect effect) {
        super(effect);
    }

    @Override
    public SearingRaysEffect copy() {
        return new SearingRaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && controller.choose(outcome, choice, game) && choice.getColor() != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent(choice.getColor().getDescription() + " creatures");
            filter.add(new ColorPredicate(choice.getColor()));
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                int amount = game.getBattlefield().countAll(filter, playerId, game);
                if (amount > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(amount, source.getSourceId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}