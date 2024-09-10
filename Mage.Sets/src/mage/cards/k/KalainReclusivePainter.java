package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KalainReclusivePainter extends CardImpl {

    public KalainReclusivePainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Kalain, Reclusive Painter enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Other creatures you control enter the battlefield with an additional +1/+1 counter on them for each mana from a Treasure spent to cast them.
        this.addAbility(new SimpleStaticAbility(new KalainReclusivePainterEffect()));
    }

    private KalainReclusivePainter(final KalainReclusivePainter card) {
        super(card);
    }

    @Override
    public KalainReclusivePainter copy() {
        return new KalainReclusivePainter(this);
    }
}

class KalainReclusivePainterEffect extends ReplacementEffectImpl {

    KalainReclusivePainterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "other creatures you control enter the battlefield with " +
                "an additional +1/+1 counter on them for each mana from a Treasure spent to cast them";
    }

    private KalainReclusivePainterEffect(final KalainReclusivePainterEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && !creature.getId().equals(source.getSourceId())
                && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        int manaPaid = ManaPaidSourceWatcher.getTreasurePaid(creature.getId(), game);
        if (manaPaid < 1) {
            return false;
        }
        creature.addCounters(
                CounterType.P1P1.createInstance(manaPaid),
                source.getControllerId(), source, game, event.getAppliedEffects()
        );
        return false;
    }

    @Override
    public KalainReclusivePainterEffect copy() {
        return new KalainReclusivePainterEffect(this);
    }
}
