package mage.player.ai;

import mage.MageItem;
import mage.MageObject;
import mage.cards.Card;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.score.GameStateEvaluator2;
import mage.players.PlayableObjectsList;
import mage.players.Player;

import java.util.Comparator;
import java.util.UUID;

/**
 * AI related code - compare and sort possible targets due target/effect type
 *
 * @author JayDi85
 */
public class PossibleTargetsComparator {

    UUID abilityControllerId;
    Game game;
    PlayableObjectsList playableItems = new PlayableObjectsList();

    public PossibleTargetsComparator(UUID abilityControllerId, Game game) {
        this.abilityControllerId = abilityControllerId;
        this.game = game;
    }

    public void findPlayableItems() {
        this.playableItems = this.game.getPlayer(this.abilityControllerId).getPlayableObjects(this.game, Zone.ALL);
    }

    private int getScoreFromBattlefield(MageItem item) {
        if (item instanceof Permanent) {
            // use battlefield score instead simple life
            return GameStateEvaluator2.evaluatePermanent((Permanent) item, game, false);
        } else {
            return getScoreFromLife(item);
        }
    }

    private String getName(MageItem item) {
        if (item instanceof Player) {
            return ((Player) item).getName();
        } else if (item instanceof MageObject) {
            return ((MageObject) item).getName();
        } else {
            return "unknown";
        }
    }

    public static int getLifeForDamage(MageItem item, Game game) {
        int res = 0;
        if (item instanceof Player) {
            res = ((Player) item).getLife();
        } else if (item instanceof Card) {
            Card card = (Card) item;
            if (card.isPlaneswalker(game)) {
                res = card.getCounters(game).getCount(CounterType.LOYALTY);
            } else if (card.isBattle(game)) {
                res = card.getCounters(game).getCount(CounterType.DEFENSE);
            } else {
                int damage = 0;
                if (card instanceof Permanent) {
                    damage = ((Permanent) card).getDamage();
                }
                res = Math.max(0, card.getToughness().getValue() - damage);
            }
        }
        return res;
    }

    private int getScoreFromLife(MageItem item) {
        // TODO: replace permanent/card life by battlefield score?
        int res = getLifeForDamage(item, game);
        if (res == 0 && item instanceof Card) {
            res = ((Card) item).getManaValue();
        }
        return res;
    }

    private boolean isMyItem(MageItem item) {
        return PossibleTargetsSelector.isMyItem(this.abilityControllerId, item);
    }

    // sort by name-id at the end, so AI will use same choices in all simulations
    private final Comparator<MageItem> BY_NAME = (o1, o2) -> getName(o2).compareTo(getName(o1));
    private final Comparator<MageItem> BY_ID = Comparator.comparing(MageItem::getId);

    private final Comparator<MageItem> BY_ME = (o1, o2) -> Boolean.compare(
            isMyItem(o2),
            isMyItem(o1)
    );

    private final Comparator<MageItem> BY_BIGGER_SCORE = (o1, o2) -> Integer.compare(
            getScoreFromBattlefield(o2),
            getScoreFromBattlefield(o1)
    );

    private final Comparator<MageItem> BY_PLAYABLE = (o1, o2) -> Boolean.compare(
            this.playableItems.containsObject(o2.getId()),
            this.playableItems.containsObject(o1.getId())
    );

    private final Comparator<MageItem> BY_LAND = (o1, o2) -> {
        boolean isLand1 = o1 instanceof MageObject && ((MageObject) o1).isLand(game);
        boolean isLand2 = o2 instanceof MageObject && ((MageObject) o2).isLand(game);
        return Boolean.compare(isLand2, isLand1);
    };

    private final Comparator<MageItem> BY_TYPE_PLAYER = (o1, o2) -> Boolean.compare(
            o2 instanceof Player,
            o1 instanceof Player
    );

    private final Comparator<MageItem> BY_TYPE_PLANESWALKER = (o1, o2) -> {
        boolean isPlaneswalker1 = o1 instanceof MageObject && ((MageObject) o1).isPlaneswalker(game);
        boolean isPlaneswalker2 = o2 instanceof MageObject && ((MageObject) o2).isPlaneswalker(game);
        return Boolean.compare(isPlaneswalker2, isPlaneswalker1);
    };

    private final Comparator<MageItem> BY_TYPE_BATTLE = (o1, o2) -> {
        boolean isBattle1 = o1 instanceof MageObject && ((MageObject) o1).isBattle(game);
        boolean isBattle2 = o2 instanceof MageObject && ((MageObject) o2).isBattle(game);
        return Boolean.compare(isBattle2, isBattle1);
    };

    private final Comparator<MageItem> BY_TYPES = BY_TYPE_PLANESWALKER
            .thenComparing(BY_TYPE_BATTLE)
            .thenComparing(BY_TYPE_PLAYER);

    /**
     * Default sorting for good effects - put the biggest items to the top
     */
    public final Comparator<MageItem> ANY_MOST_VALUABLE_FIRST = BY_TYPES
            .thenComparing(BY_BIGGER_SCORE)
            .thenComparing(BY_NAME)
            .thenComparing(BY_ID);
    public final Comparator<MageItem> ANY_MOST_VALUABLE_LAST = ANY_MOST_VALUABLE_FIRST.reversed();

    /**
     * Sorting for discard effects - put the biggest unplayable at the top, lands at the end anyway
     */
    public final Comparator<MageItem> ANY_UNPLAYABLE_AND_USELESS = BY_LAND.reversed()
            .thenComparing(BY_PLAYABLE.reversed())
            .thenComparing(ANY_MOST_VALUABLE_FIRST);

}
