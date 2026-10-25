package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class DrJosephMBenga extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public DrJosephMBenga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other creatures you control with counters on them have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // If you would put one or more counters on a creature you control, put twice that many of each of those kinds of counters on that creature instead.
        this.addAbility(new SimpleStaticAbility(new DrJosephMBengaEffect()));
    }

    private DrJosephMBenga(final DrJosephMBenga card) {
        super(card);
    }

    @Override
    public DrJosephMBenga copy() {
        return new DrJosephMBenga(this);
    }
}

class DrJosephMBengaEffect extends ReplacementEffectImpl {

    DrJosephMBengaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if you would put one or more counters on a creature you control, put twice that many of each of those kinds of counters on that creature instead";
    }

    private DrJosephMBengaEffect(final DrJosephMBengaEffect effect) {
        super(effect);
    }

    @Override
    public DrJosephMBengaEffect copy() {
        return new DrJosephMBengaEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(event.getPlayerId()) || event.getAmount() <= 0) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && permanent.isCreature(game)
            && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowMultiply(event.getAmount(), 2), true);
        return false;
    }
}
