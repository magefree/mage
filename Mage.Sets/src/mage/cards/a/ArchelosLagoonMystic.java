package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchelosLagoonMystic extends CardImpl {

    public ArchelosLagoonMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // As long as Archelos, Lagoon Mystic is tapped, other permanents enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new ArchelosLagoonMysticEffect(true)));

        // As long as Archelos, Lagoon Mystic is untapped, other permanents enter the battlefield untapped.
        this.addAbility(new SimpleStaticAbility(new ArchelosLagoonMysticEffect(false)));
    }

    private ArchelosLagoonMystic(final ArchelosLagoonMystic card) {
        super(card);
    }

    @Override
    public ArchelosLagoonMystic copy() {
        return new ArchelosLagoonMystic(this);
    }
}

class ArchelosLagoonMysticEffect extends ReplacementEffectImpl {

    private final boolean tapped;

    ArchelosLagoonMysticEffect(boolean tapped) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.tapped = tapped;
        staticText = "as long as {this} is "
                + (tapped ? "" : "un") + "tapped, other permanents enter the battlefield "
                + (tapped ? "" : "un") + "tapped";
    }

    private ArchelosLagoonMysticEffect(final ArchelosLagoonMysticEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(tapped);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }

        Permanent targetObject = ((EntersTheBattlefieldEvent) event).getTarget();
        if (targetObject == null) {
            return false;
        }

        return !sourceObject.getId().equals(targetObject.getId())
                && sourceObject.isTapped() == this.tapped;
    }

    @Override
    public ArchelosLagoonMysticEffect copy() {
        return new ArchelosLagoonMysticEffect(this);
    }
}
