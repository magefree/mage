package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class RepelTheAbominable extends CardImpl {

    public RepelTheAbominable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Prevent all damage that would be dealt this turn by non-Human sources.
        this.getSpellAbility().addEffect(new RepelTheAbominablePreventionEffect());
    }

    private RepelTheAbominable(final RepelTheAbominable card) {
        super(card);
    }

    @Override
    public RepelTheAbominable copy() {
        return new RepelTheAbominable(this);
    }
}

class RepelTheAbominablePreventionEffect extends PreventionEffectImpl {

    public RepelTheAbominablePreventionEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "prevent all damage that would be dealt this turn by non-Human sources";
    }

    private RepelTheAbominablePreventionEffect(final RepelTheAbominablePreventionEffect effect) {
        super(effect);
    }

    @Override
    public RepelTheAbominablePreventionEffect copy() {
        return new RepelTheAbominablePreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }
        MageObject sourceObject = game.getObject(event.getSourceId());
        return sourceObject != null && !sourceObject.hasSubtype(SubType.HUMAN, game);
    }
}
