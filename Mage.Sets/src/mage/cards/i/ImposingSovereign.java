package mage.cards.i;

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
 * @author jeffwadsworth
 */
public final class ImposingSovereign extends CardImpl {

    public ImposingSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN, SubType.NOBLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ImposingSovereignEffect()));

    }

    private ImposingSovereign(final ImposingSovereign card) {
        super(card);
    }

    @Override
    public ImposingSovereign copy() {
        return new ImposingSovereign(this);
    }
}

class ImposingSovereignEffect extends ReplacementEffectImpl {

    ImposingSovereignEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Creatures your opponents control enter the battlefield tapped";
    }

    private ImposingSovereignEffect(final ImposingSovereignEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.tap(game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && permanent.isCreature();
    }

    @Override
    public ImposingSovereignEffect copy() {
        return new ImposingSovereignEffect(this);
    }
}
