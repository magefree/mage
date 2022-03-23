package mage.cards.k;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class KodamaOfTheEastTree extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public KodamaOfTheEastTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever another permanent enters the battlefield under your control, if it wasn't put onto the battlefield with this ability, you may put a permanent card with equal or lesser converted mana cost from your hand onto the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldAllTriggeredAbility(new KodamaOfTheEastTreeEffect(), filter),
                KodamaOfTheEastTreeCondition.instance, "Whenever another permanent enters the battlefield " +
                "under your control, if it wasn't put onto the battlefield with this ability, you may put " +
                "a permanent card with equal or lesser mana value from your hand onto the battlefield."
        ), new KodamaOfTheEastTreeWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KodamaOfTheEastTree(final KodamaOfTheEastTree card) {
        super(card);
    }

    @Override
    public KodamaOfTheEastTree copy() {
        return new KodamaOfTheEastTree(this);
    }
}

enum KodamaOfTheEastTreeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        for (Effect effect : source.getEffects()) {
            Object obj = effect.getValue("permanentEnteringBattlefield");
            if (obj instanceof Permanent) {
                permanent = (Permanent) obj;
                break;
            }
        }
        KodamaOfTheEastTreeWatcher watcher = game.getState().getWatcher(KodamaOfTheEastTreeWatcher.class);
        return watcher != null && !watcher.checkPermanent(permanent, source, game);
    }
}

class KodamaOfTheEastTreeEffect extends OneShotEffect {

    KodamaOfTheEastTreeEffect() {
        super(Outcome.Benefit);
    }

    private KodamaOfTheEastTreeEffect(final KodamaOfTheEastTreeEffect effect) {
        super(effect);
    }

    @Override
    public KodamaOfTheEastTreeEffect copy() {
        return new KodamaOfTheEastTreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object obj = this.getValue("permanentEnteringBattlefield");
        if (!(obj instanceof Permanent)) {
            return false;
        }
        Permanent permanent = (Permanent) obj;
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterCard filter = new FilterPermanentCard(
                "a permanent card with mana value " + permanent.getManaValue() + " or less"
        );
        filter.add(new ManaValuePredicate(
                ComparisonType.FEWER_THAN, permanent.getManaValue() + 1
        ));
        TargetCardInHand target = new TargetCardInHand(filter);
        if (!target.canChoose(source.getControllerId(), source, game)
                || !player.chooseUse(outcome, "Put a permanent card onto the battlefield?", source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent otherPermanent = game.getPermanent(card.getId());
        if (otherPermanent == null) {
            return false;
        }
        KodamaOfTheEastTreeWatcher watcher = game.getState().getWatcher(KodamaOfTheEastTreeWatcher.class);
        if (watcher != null) {
            watcher.addPermanent(otherPermanent, source, game);
        }
        return true;
    }
}

class KodamaOfTheEastTreeWatcher extends Watcher {

    private final Map<String, Set<MageObjectReference>> morMap = new HashMap<>();

    KodamaOfTheEastTreeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
    }

    void addPermanent(Permanent permanent, Ability source, Game game) {
        morMap.computeIfAbsent(getKey(source, game), x -> new HashSet<>())
                .add(new MageObjectReference(permanent, game));
    }

    boolean checkPermanent(Permanent permanent, Ability source, Game game) {
        return morMap
                .computeIfAbsent(getKey(source, game), x -> new HashSet<>())
                .stream()
                .anyMatch(mor -> mor.refersTo(permanent, game));
    }

    private static String getKey(Ability source, Game game) {
        return "" + source.getSourceId() + game.getState().getZoneChangeCounter(source.getSourceId());
    }
}
