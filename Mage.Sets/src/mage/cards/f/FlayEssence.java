package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class FlayEssence extends CardImpl {

    public FlayEssence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Exile target creature or planeswalker. You gain life equal to the number of counters on it.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new FlayEssenceEffect());
    }

    private FlayEssence(final FlayEssence card) {
        super(card);
    }

    @Override
    public FlayEssence copy() {
        return new FlayEssence(this);
    }
}

class FlayEssenceEffect extends OneShotEffect {

    public FlayEssenceEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature or planeswalker. You gain life equal to the number of counters on it";
    }

    private FlayEssenceEffect(final FlayEssenceEffect effect) {
        super(effect);
    }

    @Override
    public FlayEssenceEffect copy() {
        return new FlayEssenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            int life = 0;
            for (Counter counter : permanent.getCounters(game).values()) {
                life += counter.getCount();
            }
            controller.moveCards(permanent, Zone.EXILED, source, game);
            controller.gainLife(life, game, source);
            return true;
        }
        return false;
    }
}
