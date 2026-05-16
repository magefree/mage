package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlDiedCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class GormaTheGullet extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creatures that died under your control this turn",
            CreaturesYouControlDiedCount.instance
    );

    public GormaTheGullet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PEST);
        this.subtype.add(SubType.FROG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another creature you control dies, put a +1/+1 counter on Gorma.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));

        // Nontoken creatures you control enter with an additional +1/+1 counter on them for each creature that died under your control this turn.
        this.addAbility(new SimpleStaticAbility(new GormaTheGulletEffect()).addHint(hint));
    }

    private GormaTheGullet(final GormaTheGullet card) {
        super(card);
    }

    @Override
    public GormaTheGullet copy() {
        return new GormaTheGullet(this);
    }
}

class GormaTheGulletEffect extends ReplacementEffectImpl {

    GormaTheGulletEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Nontoken creatures you control enter with an additional +1/+1 counter on them " +
                "for each creature that died under your control this turn";
    }

    private GormaTheGulletEffect(final GormaTheGulletEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null
                && permanent.isCreature(game)
                && !(permanent instanceof PermanentToken)
                && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        int amount = watcher == null ? 0 : watcher.getAmountOfCreaturesDiedThisTurnByController(source.getControllerId());
        if (permanent != null && amount > 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public GormaTheGulletEffect copy() {
        return new GormaTheGulletEffect(this);
    }
}
