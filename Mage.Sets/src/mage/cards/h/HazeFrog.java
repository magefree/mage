
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class HazeFrog extends CardImpl {

    public HazeFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Haze Frog enters the battlefield, prevent all combat damage that other creatures would deal this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HazeFrogEffect()));
    }

    public HazeFrog(final HazeFrog card) {
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
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
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
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage() && !damageEvent.getSourceId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }
}

