package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class VampireSocialite extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.VAMPIRE, "other Vampire you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VampireSocialite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Vampire Socialite enters the battlefield, if an opponent lost life this turn, put a +1/+1 counter on each other Vampire you control.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)),
                OpponentsLostLifeCondition.instance,
                "When {this} enters the battlefield, if an opponent lost life this turn, put a +1/+1 counter on each other Vampire you control."
        ));

        // As long as an opponent lost life this turn, each other Vampire you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new VampireSocialiteReplacementEffect(filter)));
    }

    private VampireSocialite(final VampireSocialite card) {
        super(card);
    }

    @Override
    public VampireSocialite copy() {
        return new VampireSocialite(this);
    }
}

class VampireSocialiteReplacementEffect extends ReplacementEffectImpl {

    private final FilterPermanent filter;

    public VampireSocialiteReplacementEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        this.filter = filter;
        staticText = "As long as an opponent lost life this turn, each other Vampire you control enters the battlefield with an additional +1/+1 counter on it";
    }

    private VampireSocialiteReplacementEffect(final VampireSocialiteReplacementEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public VampireSocialiteReplacementEffect copy() {
        return new VampireSocialiteReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (OpponentsLostLifeCondition.instance.apply(game, source)) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            return permanent != null && filter.match(permanent, source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }
}
