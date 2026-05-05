package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author greenlovecat
 * 
 * 
 * When this enchantment enters, put a flying counter on each face-down creature you control.
 *
 * Face-down creatures you control enter with a flying counter on them.
 * 
 * At the beginning of your upkeep, you may cloak the top card of your library.
 */

public final class VeiledAscension extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a face-down creature");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public VeiledAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Veiled Ascension enters the battlefield, put a flying counter on each face-down creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.FLYING.createInstance(), filter)
        ));

        // Face-down creatures you control enter the battlefield with a flying counter on them.
        this.addAbility(new SimpleStaticAbility(new VeiledAscensionEffect()));

        // At the beginning of your upkeep, you may cloak the top card of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ManifestEffect(StaticValue.get(1), true, true), true));
    }

    private VeiledAscension(final VeiledAscension card) {
        super(card);
    }

    @Override
    public VeiledAscension copy() {
        return new VeiledAscension(this);
    }

}

class VeiledAscensionEffect extends ReplacementEffectImpl {

    VeiledAscensionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Face-down creatures you control enter the battlefield with a flying counter on them.";
    }

    private VeiledAscensionEffect(VeiledAscensionEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && creature.isFaceDown(game)
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (creature == null || controller == null) {
            return false;
        }
        creature.addCounters(CounterType.FLYING.createInstance(), source.getControllerId(), source, game);

        return false;
    }

    @Override
    public VeiledAscensionEffect copy() {
        return new VeiledAscensionEffect(this);
    }
}