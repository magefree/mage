package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvadeTheCity extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Instants and sorceries in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY)
    );

    public InvadeTheCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{R}");

        // Amass X, where X is the number of instant and sorcery cards in your graveyard.
        this.getSpellAbility().addEffect(new InvadeTheCityEffect());
    }

    private InvadeTheCity(final InvadeTheCity card) {
        super(card);
    }

    @Override
    public InvadeTheCity copy() {
        return new InvadeTheCity(this);
    }
}

class InvadeTheCityEffect extends OneShotEffect {

    InvadeTheCityEffect() {
        super(Outcome.Benefit);
        staticText = "amass Zombies X, where X is the number of instant and sorcery cards in your graveyard. " +
                "<i>(Put X +1/+1 counterson an Army you control. It's also a Zombie. If you don't control an Army, " +
                "create a 0/0 black Zombie Army creature token first.)</i>";
    }

    private InvadeTheCityEffect(final InvadeTheCityEffect effect) {
        super(effect);
    }

    @Override
    public InvadeTheCityEffect copy() {
        return new InvadeTheCityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return AmassEffect.doAmass(player.getGraveyard().count(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game
        ), SubType.ZOMBIE, game, source) != null;
    }
}
