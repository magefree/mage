
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class HotheadedGiant extends CardImpl {

    public HotheadedGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Hotheaded Giant enters the battlefield with two -1/-1 counters on it unless you've cast another red spell this turn.
        Condition condition = new CastRedSpellThisTurnCondition();
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), new InvertCondition(condition), ""), "with two -1/-1 counters on it unless you've cast another red spell this turn"), new HotHeadedGiantWatcher(this.getId()));

    }

    private HotheadedGiant(final HotheadedGiant card) {
        super(card);
    }

    @Override
    public HotheadedGiant copy() {
        return new HotheadedGiant(this);
    }
}

class CastRedSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        HotHeadedGiantWatcher watcher = game.getState().getWatcher(HotHeadedGiantWatcher.class, source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class HotHeadedGiantWatcher extends Watcher {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    private UUID cardId;

    public HotHeadedGiantWatcher(UUID cardId) {
        super(WatcherScope.PLAYER);
        this.cardId = cardId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && controllerId.equals(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (!spell.getSourceId().equals(cardId) && filter.match(spell, game)) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        condition = false;
    }
}
