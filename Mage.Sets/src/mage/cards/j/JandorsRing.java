package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInHand;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class JandorsRing extends CardImpl {

    private static final FilterCard filter = new FilterCard("the last card you drew this turn");

    static {
        filter.add(JandorsRingPredicate.instance);
    }

    public JandorsRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {2}, {tap}, Discard the last card you drew this turn: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(filter)).setText("discard the last card you drew this turn"));
        this.addAbility(ability, new JandorsRingWatcher());
    }

    private JandorsRing(final JandorsRing card) {
        super(card);
    }

    @Override
    public JandorsRing copy() {
        return new JandorsRing(this);
    }
}

enum JandorsRingPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return JandorsRingWatcher.checkCard(input, game);
    }
}

class JandorsRingWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    JandorsRingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            set.add(event.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkCard(Card card, Game game) {
        return card != null
                && game
                .getState()
                .getWatcher(JandorsRingWatcher.class)
                .set
                .contains(card.getId());
    }
}
