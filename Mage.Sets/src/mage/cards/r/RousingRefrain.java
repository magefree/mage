package mage.cards.r;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RousingRefrain extends CardImpl {

    public RousingRefrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Add {R} for each card in target opponent's hand. Until end of turn, you don't lose this mana as steps and phases end. Exile Rousing Refrain with three time counters on it.
        this.getSpellAbility().addEffect(new RousingRefrainEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new ExileSpellWithTimeCountersEffect(3));

        // Suspend 3—{1}{R}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{R}"), this));
    }

    private RousingRefrain(final RousingRefrain card) {
        super(card);
    }

    @Override
    public RousingRefrain copy() {
        return new RousingRefrain(this);
    }
}

class RousingRefrainEffect extends OneShotEffect {

    RousingRefrainEffect() {
        super(Outcome.Benefit);
        staticText = "Add {R} for each card in target opponent's hand. " +
                "Until end of turn, you don't lose this mana as steps and phases end";
    }

    private RousingRefrainEffect(final RousingRefrainEffect effect) {
        super(effect);
    }

    @Override
    public RousingRefrainEffect copy() {
        return new RousingRefrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null || player.getHand().isEmpty()) {
            return false;
        }
        controller.getManaPool().addMana(
                new Mana(ManaType.RED, player.getHand().size()),
                game, source, true
        );
        return true;
    }
}
