package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class PersonalSanctuary extends CardImpl {

    public PersonalSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");


        // During your turn, prevent all damage that would be dealt to you.
        this.addAbility(new SimpleStaticAbility(new PersonalSanctuaryEffect()));
    }

    private PersonalSanctuary(final PersonalSanctuary card) {
        super(card);
    }

    @Override
    public PersonalSanctuary copy() {
        return new PersonalSanctuary(this);
    }
}

class PersonalSanctuaryEffect extends PreventionEffectImpl {

    PersonalSanctuaryEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "During your turn, prevent all damage that would be dealt to you";
    }

    private PersonalSanctuaryEffect(final PersonalSanctuaryEffect effect) {
        super(effect);
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
