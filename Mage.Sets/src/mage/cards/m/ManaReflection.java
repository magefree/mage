
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;

/**
 *
 * @author jeffwadsworth
 */
public final class ManaReflection extends CardImpl {

    public ManaReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}{G}");

        // If you tap a permanent for mana, it produces twice as much of that mana instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ManaReflectionReplacementEffect()));

    }

    public ManaReflection(final ManaReflection card) {
        super(card);
    }

    @Override
    public ManaReflection copy() {
        return new ManaReflection(this);
    }
}

class ManaReflectionReplacementEffect extends ReplacementEffectImpl {

    ManaReflectionReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you tap a permanent for mana, it produces twice as much of that mana instead";
    }

    ManaReflectionReplacementEffect(ManaReflectionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Mana mana = ((ManaEvent) event).getMana();
        if (mana.getBlack() > 0) {
            mana.set(ManaType.BLACK, mana.getBlack() * 2);
        }
        if (mana.getBlue() > 0) {
            mana.set(ManaType.BLUE, mana.getBlue() * 2);
        }
        if (mana.getWhite() > 0) {
            mana.set(ManaType.WHITE, mana.getWhite() * 2);
        }
        if (mana.getGreen() > 0) {
            mana.set(ManaType.GREEN, mana.getGreen() * 2);
        }
        if (mana.getRed() > 0) {
            mana.set(ManaType.RED, mana.getRed() * 2);
        }
        if (mana.getColorless() > 0) {
            mana.set(ManaType.COLORLESS, mana.getColorless() * 2);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId())
                && game.getPermanentOrLKIBattlefield(event.getSourceId()) != null;
    }

    @Override
    public ManaReflectionReplacementEffect copy() {
        return new ManaReflectionReplacementEffect(this);
    }
}
