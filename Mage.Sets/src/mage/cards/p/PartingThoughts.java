
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PartingThoughts extends CardImpl {

    public PartingThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Destroy target creature. You draw X cards and you lose X life, where X is the number of counters on that creature.
        this.getSpellAbility().addEffect(new PartingThoughtsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PartingThoughts(final PartingThoughts card) {
        super(card);
    }

    @Override
    public PartingThoughts copy() {
        return new PartingThoughts(this);
    }
}

class PartingThoughtsEffect extends OneShotEffect {

    public PartingThoughtsEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy target creature. You draw X cards and you lose X life, where X is the number of counters on that creature";
    }

    private PartingThoughtsEffect(final PartingThoughtsEffect effect) {
        super(effect);
    }

    @Override
    public PartingThoughtsEffect copy() {
        return new PartingThoughtsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.destroy(source, game, false);
                int numberOfCounters = 0;
                for (Counter counter : permanent.getCounters(game).values()) {
                    numberOfCounters += counter.getCount();
                }
                if (numberOfCounters > 0) {
                    controller.drawCards(numberOfCounters, source, game);
                    controller.loseLife(numberOfCounters, game, source, false);
                }
            }
            return true;
        }
        return false;
    }
}
