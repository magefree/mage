package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class ArchonOfEmeria extends CardImpl {

    public ArchonOfEmeria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each player can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(new CantCastMoreThanOneSpellEffect(TargetController.ANY)));

        // Nonbasic lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new ArchonOfEmeriaEffect()));
    }

    private ArchonOfEmeria(final ArchonOfEmeria card) {
        super(card);
    }

    @Override
    public ArchonOfEmeria copy() {
        return new ArchonOfEmeria(this);
    }
}

class ArchonOfEmeriaEffect extends ReplacementEffectImpl {

    ArchonOfEmeriaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "nonbasic lands your opponents control enter the battlefield tapped";
    }

    private ArchonOfEmeriaEffect(final ArchonOfEmeriaEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isLand() && !permanent.isBasic()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArchonOfEmeriaEffect copy() {
        return new ArchonOfEmeriaEffect(this);
    }
}
