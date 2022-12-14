package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class Betray extends CardImpl {
    public Betray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        //Target creature an opponent controls deals damage to its controller equal to that creature's power.
        this.getSpellAbility().addEffect(new BetrayEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 1,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, false));
    }

    public Betray(final Betray card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new Betray(this);
    }
}

class BetrayEffect extends OneShotEffect {

    BetrayEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature an opponent controls deals damage to its controller equal to that creature's power.";
    }

    private BetrayEffect(final BetrayEffect effect) {
        super(effect);
    }

    @Override
    public BetrayEffect copy() {
        return new BetrayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        return player.damage(permanent.getPower().getValue(), permanent.getId(), source, game) > 0;
    }
}
