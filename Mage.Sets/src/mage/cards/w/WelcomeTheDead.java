package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.ZombieDruidToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WelcomeTheDead extends CardImpl {

    public WelcomeTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Draw two cards, then discard a card and you lose 2 life. Create X tapped 2/2 black Zombie Druid creature tokens, where X is the number of cards that were put into your graveyard from your hand or library this turn.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new ZombieDruidToken(), WelcomeTheDeadValue.instance, true, false
        ));
        this.getSpellAbility().addHint(WelcomeTheDeadValue.getHint());
        this.getSpellAbility().addWatcher(new WelcomeTheDeadWatcher());

        // Flashback {5}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{B}")));
    }

    private WelcomeTheDead(final WelcomeTheDead card) {
        super(card);
    }

    @Override
    public WelcomeTheDead copy() {
        return new WelcomeTheDead(this);
    }
}

enum WelcomeTheDeadValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Cards put into your graveyard from your hand or library", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return WelcomeTheDeadWatcher.getValue(game, sourceAbility);
    }

    @Override
    public WelcomeTheDeadValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of cards that were put into your graveyard from your hand or library this turn";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static Hint getHint() {
        return hint;
    }
}

class WelcomeTheDeadWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    WelcomeTheDeadWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!Zone.GRAVEYARD.match(zEvent.getToZone())) {
            return;
        }
        switch (zEvent.getFromZone()) {
            case HAND:
            case LIBRARY:
                map.compute(zEvent.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getValue(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(WelcomeTheDeadWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0);
    }
}
