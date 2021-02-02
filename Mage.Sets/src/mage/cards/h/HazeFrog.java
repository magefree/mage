package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HazeFrog extends CardImpl {

    public HazeFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Haze Frog enters the battlefield, prevent all combat damage that other creatures would deal this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HazeFrogEffect()));
    }

    private HazeFrog(final HazeFrog card) {
        super(card);
    }

    @Override
    public HazeFrog copy() {
        return new HazeFrog(this);
    }
}

class HazeFrogEffect extends PreventionEffectImpl {

    public HazeFrogEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "prevent all combat damage that other creatures would deal this turn";
    }

    public HazeFrogEffect(final HazeFrogEffect effect) {
        super(effect);
    }

    @Override
    public HazeFrogEffect copy() {
        return new HazeFrogEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            Permanent permanent = game.getPermanent(event.getSourceId());
            StringBuilder message = new StringBuilder();
            if (permanent != null) {
                message.append(" from ").append(permanent.getName());
            }
            message.insert(0, "Damage").append(" has been prevented: ").append(damage);
            event.setAmount(0);
            game.informPlayers(message.toString());
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent) {
            DamageEvent damageEvent = (DamageEvent) event;
            return damageEvent.isCombatDamage() && !damageEvent.getSourceId().equals(source.getSourceId());
        }
        return false;
    }
}

