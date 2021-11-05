package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeirloomMirror extends CardImpl {

    public HeirloomMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.secondSideCardClazz = mage.cards.i.InheritedFiend.class;

        // {1}, {T}, Pay 1 life, Discard a card: Draw a card, mill a card, then put a ritual counter on Heirloom Mirror. Then if it has 3 or more ritual counters on it, remove them and transform it. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(new HeirloomMirrorEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private HeirloomMirror(final HeirloomMirror card) {
        super(card);
    }

    @Override
    public HeirloomMirror copy() {
        return new HeirloomMirror(this);
    }
}

class HeirloomMirrorEffect extends OneShotEffect {

    HeirloomMirrorEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, mill a card, then put a ritual counter on {this}. " +
                "Then if it has three or more ritual counters on it, remove them and transform it";
    }

    private HeirloomMirrorEffect(final HeirloomMirrorEffect effect) {
        super(effect);
    }

    @Override
    public HeirloomMirrorEffect copy() {
        return new HeirloomMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        player.drawCards(1, source, game);
        player.millCards(1, source, game);
        permanent.addCounters(CounterType.RITUAL.createInstance(), source.getControllerId(), source, game);
        int counters = permanent.getCounters(game).getCount(CounterType.RITUAL);
        if (counters < 3) {
            return true;
        }
        permanent.removeCounters(CounterType.RITUAL.createInstance(counters), source, game);
        new TransformSourceEffect().apply(game, source);
        return true;
    }
}
