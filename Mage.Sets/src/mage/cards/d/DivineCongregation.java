
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DivineCongregation extends CardImpl {

    public DivineCongregation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // You gain 2 life for each creature target player controls.
        this.getSpellAbility().addEffect(new DivineCongregationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Suspend 5-{1}{W}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl<>("{1}{W}"), this));
    }

    private DivineCongregation(final DivineCongregation card) {
        super(card);
    }

    @Override
    public DivineCongregation copy() {
        return new DivineCongregation(this);
    }
}

class DivineCongregationEffect extends OneShotEffect {

    DivineCongregationEffect() {
        super(Outcome.Benefit);
        staticText = "You gain 2 life for each creature target player controls";
    }

    private DivineCongregationEffect(final DivineCongregationEffect effect) {
        super(effect);
    }

    @Override
    public DivineCongregationEffect copy() {
        return new DivineCongregationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller != null && player != null) {
            int critters = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game).size();
            controller.gainLife(2 * critters, game, source);
        }
        return true;
    }
}
