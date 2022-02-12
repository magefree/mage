package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamiOfTransience extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public KamiOfTransience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast an enchantment spell, put a +1/+1 counter on Kami of Transience.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));

        // At the beginning of each end step, if an enchantment was put into your graveyard from the battlefield this turn, you may return Kami of Transience from your graveyard to your hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                TargetController.ANY, KamiOfTransienceCondition.instance, true
        ).addHint(KamiOfTransienceCondition.getHint()), new KamiOfTransienceWatcher());
    }

    private KamiOfTransience(final KamiOfTransience card) {
        super(card);
    }

    @Override
    public KamiOfTransience copy() {
        return new KamiOfTransience(this);
    }
}

enum KamiOfTransienceCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "An enchantment went to your graveyard this turn");

    @Override
    public boolean apply(Game game, Ability source) {
        return KamiOfTransienceWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "if an enchantment was put into your graveyard from the battlefield this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}

class KamiOfTransienceWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    KamiOfTransienceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !zEvent.getTarget().isEnchantment(game)) {
            return;
        }
        playerSet.add(zEvent.getTarget().getOwnerId());
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    public static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(KamiOfTransienceWatcher.class)
                .playerSet
                .contains(source.getControllerId());
    }
}
