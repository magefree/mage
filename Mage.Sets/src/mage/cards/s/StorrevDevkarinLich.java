package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StorrevDevkarinLich extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "creature or planeswalker card in your graveyard that wasn't put there this combat"
    );

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
        filter.add(StorrevDevkarinLichPredicate.instance);
    }

    public StorrevDevkarinLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Storrev, Devkarin Lich deals combat damage to a player or planeswalker, return to your hand target creature or planeswalker card in your graveyard that wasn't put there this combat.
        Ability ability = new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability, new StorrevDevkarinLichWatcher());
    }

    private StorrevDevkarinLich(final StorrevDevkarinLich card) {
        super(card);
    }

    @Override
    public StorrevDevkarinLich copy() {
        return new StorrevDevkarinLich(this);
    }
}

enum StorrevDevkarinLichPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        StorrevDevkarinLichWatcher watcher = game.getState().getWatcher(StorrevDevkarinLichWatcher.class);
        if (watcher == null) {
            return false;
        }
        return !watcher.wasPutInAGraveyardThisCombat(input.getId(), game);
    }
}

class StorrevDevkarinLichWatcher extends Watcher {

    private final Set<MageObjectReference> cards = new HashSet<>();

    StorrevDevkarinLichWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            cards.clear();
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && (card.isCreature(game) || card.isPlaneswalker(game))) {
                cards.add(new MageObjectReference(card, game));
            }
        }
    }


    boolean wasPutInAGraveyardThisCombat(UUID cardId, Game game) {
        for (MageObjectReference mor : cards) {
            if (mor.refersTo(cardId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
