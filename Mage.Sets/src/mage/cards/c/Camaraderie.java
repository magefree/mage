package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Camaraderie extends CardImpl {

    public Camaraderie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{W}");

        // You gain X life and draw X cards, where X is the number of creatures you control. Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new CamaraderieEffect());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private Camaraderie(final Camaraderie card) {
        super(card);
    }

    @Override
    public Camaraderie copy() {
        return new Camaraderie(this);
    }
}

class CamaraderieEffect extends OneShotEffect {

    public CamaraderieEffect() {
        super(Outcome.Benefit);
        this.staticText = "You gain X life and draw X cards, "
                + "where X is the number of creatures you control. "
                + "Creatures you control get +1/+1 until end of turn.";
    }

    public CamaraderieEffect(final CamaraderieEffect effect) {
        super(effect);
    }

    @Override
    public CamaraderieEffect copy() {
        return new CamaraderieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        );
        player.gainLife(xValue, game, source);
        player.drawCards(xValue, source, game);
        game.addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn), source);
        return true;
    }
}
