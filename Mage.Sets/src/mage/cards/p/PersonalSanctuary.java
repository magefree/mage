
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author nantuko
 */
public final class PersonalSanctuary extends CardImpl {

    public PersonalSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");


        // During your turn, prevent all damage that would be dealt to you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PersonalSanctuaryEffect()));
    }

    public PersonalSanctuary(final PersonalSanctuary card) {
        super(card);
    }

    @Override
    public PersonalSanctuary copy() {
        return new PersonalSanctuary(this);
    }
}

class PersonalSanctuaryEffect extends PreventionEffectImpl {

    public PersonalSanctuaryEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "During your turn, prevent all damage that would be dealt to you";
    }

    public PersonalSanctuaryEffect(PersonalSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.informPlayers("Damage has been prevented: " + damage);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            if (event.getTargetId().equals(source.getControllerId()) && game.isActivePlayer(source.getControllerId()))
                return super.applies(event, source, game);
        }
        return false;
    }

    @Override
    public PersonalSanctuaryEffect copy() {
        return new PersonalSanctuaryEffect(this);
    }
}
