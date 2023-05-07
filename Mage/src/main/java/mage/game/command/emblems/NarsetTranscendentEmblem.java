package mage.game.command.emblems;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author spjspj
 */
public final class NarsetTranscendentEmblem extends Emblem {

    // "Your opponents can't cast noncreature spells.
    public NarsetTranscendentEmblem() {

        super("Emblem Narset");

        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new NarsetTranscendentCantCastEffect()));
    }

    private NarsetTranscendentEmblem(final NarsetTranscendentEmblem card) {
        super(card);
    }

    @Override
    public NarsetTranscendentEmblem copy() {
        return new NarsetTranscendentEmblem(this);
    }
}

class NarsetTranscendentCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public NarsetTranscendentCantCastEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = "Your opponents can't cast noncreature spells";
    }

    public NarsetTranscendentCantCastEffect(final NarsetTranscendentCantCastEffect effect) {
        super(effect);
    }

    @Override
    public NarsetTranscendentCantCastEffect copy() {
        return new NarsetTranscendentCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast can't cast noncreature spells (it is prevented by emblem of " + mageObject.getLogName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
}
