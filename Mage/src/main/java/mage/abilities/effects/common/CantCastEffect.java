package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

public class CantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public CantCastEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "Until your next turn, your opponents can't cast spells with the chosen name";
    }

    public CantCastEffect(final CantCastEffect effect) {
        super(effect);
    }

    @Override
    public CantCastEffect copy() {
        return new CantCastEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (mageObject != null && cardName != null) {
            return "You can't cast a card named " + cardName + " (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            return object != null && CardUtil.haveSameNames(object, cardName, game);
        }
        return false;
    }
}
