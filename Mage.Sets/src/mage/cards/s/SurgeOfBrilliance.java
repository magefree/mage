package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurgeOfBrilliance extends CardImpl {

    public SurgeOfBrilliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Paradox -- Draw a card for each spell you've cast this turn from anywhere other than your hand.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(SurgeOfBrillianceValue.instance));
        this.getSpellAbility().setAbilityWord(AbilityWord.PARADOX);
        this.getSpellAbility().addWatcher(new SurgeOfBrillianceWatcher());

        // Foretell {1}{U}
        this.addAbility(new ForetellAbility(this, "{1}{U}"));
    }

    private SurgeOfBrilliance(final SurgeOfBrilliance card) {
        super(card);
    }

    @Override
    public SurgeOfBrilliance copy() {
        return new SurgeOfBrilliance(this);
    }
}

enum SurgeOfBrillianceValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return SurgeOfBrillianceWatcher.getCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public SurgeOfBrillianceValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "spell you've cast this turn from anywhere other than your hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class SurgeOfBrillianceWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    SurgeOfBrillianceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.getFromZone() != Zone.HAND) {
            map.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getCount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(SurgeOfBrillianceWatcher.class)
                .map
                .getOrDefault(playerId, 0);
    }
}
