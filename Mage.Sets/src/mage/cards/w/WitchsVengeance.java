package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitchsVengeance extends CardImpl {

    public WitchsVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Creatures of the creature type of your choice get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new WitchsVengeanceEffect());
    }

    private WitchsVengeance(final WitchsVengeance card) {
        super(card);
    }

    @Override
    public WitchsVengeance copy() {
        return new WitchsVengeance(this);
    }
}

class WitchsVengeanceEffect extends OneShotEffect {

    WitchsVengeanceEffect() {
        super(Outcome.Benefit);
        staticText = "Creatures of the creature type of your choice get -3/-3 until end of turn.";
    }

    private WitchsVengeanceEffect(final WitchsVengeanceEffect effect) {
        super(effect);
    }

    @Override
    public WitchsVengeanceEffect copy() {
        return new WitchsVengeanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceCreatureType choice = new ChoiceCreatureType();
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(SubType.byDescription(choice.getChoice()).getPredicate());
        game.addEffect(new BoostAllEffect(
                -3, -3, Duration.EndOfTurn, filter, false
        ), source);
        return true;
    }
}