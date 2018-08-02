package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class RavenousSlime extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public RavenousSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ravenous Slime can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(
                new CantBeBlockedByCreaturesSourceEffect(
                        filter, Duration.WhileOnBattlefield
                )
        ));

        // If a creature an opponent controls would die, instead exile it and put a number of +1/+1 counters equal to that creature's power on Ravenous Slime.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new RavenousSlimeEffect()
        ));
    }

    public RavenousSlime(final RavenousSlime card) {
        super(card);
    }

    @Override
    public RavenousSlime copy() {
        return new RavenousSlime(this);
    }
}

class RavenousSlimeEffect extends ReplacementEffectImpl {

    public RavenousSlimeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature an opponent controls would die, "
                + "instead exile it and put a number of +1/+1 counters "
                + "equal to that creature's power on {this}";
    }

    public RavenousSlimeEffect(final RavenousSlimeEffect effect) {
        super(effect);
    }

    @Override
    public RavenousSlimeEffect copy() {
        return new RavenousSlimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (controller == null || sourceCreature == null) {
            return false;
        }
        if (((ZoneChangeEvent) event).getFromZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        controller.moveCards(permanent, Zone.EXILED, source, game);
        return new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(power)
        ).apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.GRAVEYARD) {
            return false;
        }
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent == null
                || !game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            return false;
        }
        if (zEvent.getTarget() != null) { // if it comes from permanent, check if it was a creature on the battlefield
            if (zEvent.getTarget().isCreature()) {
                return true;
            }
        } else if (permanent.isCreature()) {
            return true;
        }
        return false;
    }
}
