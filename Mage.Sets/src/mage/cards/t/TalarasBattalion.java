
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
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
public final class TalarasBattalion extends CardImpl {

    public TalarasBattalion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cast Talara's Battalion only if you've cast another green spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TalarasBattalionEffect()), new TalarasBattalionWatcher(this.getId()));

    }

    private TalarasBattalion(final TalarasBattalion card) {
        super(card);
    }

    @Override
    public TalarasBattalion copy() {
        return new TalarasBattalion(this);
    }
}

class TalarasBattalionEffect extends ContinuousRuleModifyingEffectImpl {

    TalarasBattalionEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast this spell only if you've cast another green spell this turn";
    }

    TalarasBattalionEffect(final TalarasBattalionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL
                && event.getSourceId().equals(source.getSourceId())) {
            CastGreenSpellThisTurnCondition condition = new CastGreenSpellThisTurnCondition();
            return (!condition.apply(game, source));
        }
        return false;

    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TalarasBattalionEffect copy() {
        return new TalarasBattalionEffect(this);
    }
}

class CastGreenSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        TalarasBattalionWatcher watcher = game.getState().getWatcher(TalarasBattalionWatcher.class, source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class TalarasBattalionWatcher extends Watcher {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }
    private final UUID cardId;

    public TalarasBattalionWatcher(UUID cardId) {
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
