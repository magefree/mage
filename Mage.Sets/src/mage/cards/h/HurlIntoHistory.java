package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HurlIntoHistory extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact or creature spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public HurlIntoHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target artifact or creature spell. Discover X, where X is that spell's mana value.
        this.getSpellAbility().addEffect(new HurlIntoHistoryEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private HurlIntoHistory(final HurlIntoHistory card) {
        super(card);
    }

    @Override
    public HurlIntoHistory copy() {
        return new HurlIntoHistory(this);
    }
}

/**
 * Inspired by {@link mage.cards.s.SpellSwindle}
 */
class HurlIntoHistoryEffect extends OneShotEffect {

    public HurlIntoHistoryEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target artifact or creature spell. Discover X, where X is that spell's mana value.";
    }

    private HurlIntoHistoryEffect(final HurlIntoHistoryEffect effect) {
        super(effect);
    }

    @Override
    public HurlIntoHistoryEffect copy() {
        return new HurlIntoHistoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackObject == null) {
            return false;
        }

        game.getStack().counter(source.getFirstTarget(), source, game);

        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DiscoverEffect.doDiscover(player, stackObject.getManaValue(), game, source);
        }
        return true;
    }
}
