
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class BiomanticMastery extends CardImpl {

    public BiomanticMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G/U}{G/U}{G/U}");

        // <i>({GU} can be paid with either {G} or {U}.)</i>
        // Draw a card for each creature target player controls, then draw a card for each creature another target player controls.
        this.getSpellAbility().addEffect(new BiomanticMasteryEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(2));
    }

    private BiomanticMastery(final BiomanticMastery card) {
        super(card);
    }

    @Override
    public BiomanticMastery copy() {
        return new BiomanticMastery(this);
    }
}

class BiomanticMasteryEffect extends OneShotEffect {

    public BiomanticMasteryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card for each creature target player controls, then draw a card for each creature another target player controls";
    }

    public BiomanticMasteryEffect(final BiomanticMasteryEffect effect) {
        super(effect);
    }

    @Override
    public BiomanticMasteryEffect copy() {
        return new BiomanticMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        for (UUID playerId : getTargetPointer().getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player == null) { continue; }

            int creatures = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game);
            controller.drawCards(creatures, source, game);
        }
        return true;
    }
}
