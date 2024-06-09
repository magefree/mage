
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class ProtectorOfTheCrown extends CardImpl {

    public ProtectorOfTheCrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Protector of the Crown enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // All damage would be dealt to you is dealt to Protector of the Crown instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ProtectorOfTheCrownEffect()));
    }

    private ProtectorOfTheCrown(final ProtectorOfTheCrown card) {
        super(card);
    }

    @Override
    public ProtectorOfTheCrown copy() {
        return new ProtectorOfTheCrown(this);
    }
}

class ProtectorOfTheCrownEffect extends ReplacementEffectImpl {

    ProtectorOfTheCrownEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "All damage that would be dealt to you is dealt to {this} instead";
    }

    private ProtectorOfTheCrownEffect(final ProtectorOfTheCrownEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
            return true;
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public ProtectorOfTheCrownEffect copy() {
        return new ProtectorOfTheCrownEffect(this);
    }
}
