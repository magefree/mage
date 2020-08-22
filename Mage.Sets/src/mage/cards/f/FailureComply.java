
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetSpell;

/**
 * @author spjspj
 */
public final class FailureComply extends SplitCard {

    public FailureComply(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{U}", "{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Failure
        // Return target spell to it's owner's hand
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell());
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());

        // to
        // Comply
        // Choose a card name.  Until your next turn, your opponents can't cast spells with the chosen name
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        Effect effect = new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL);
        effect.setText("Choose a card name");
        getRightHalfCard().getSpellAbility().addEffect(effect);
        getRightHalfCard().getSpellAbility().addEffect(new ComplyCantCastEffect());
    }

    public FailureComply(final FailureComply card) {
        super(card);
    }

    @Override
    public FailureComply copy() {
        return new FailureComply(this);
    }
}

class ComplyCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public ComplyCantCastEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "Until your next turn, your opponents can't cast spells with the chosen name";
    }

    public ComplyCantCastEffect(final ComplyCantCastEffect effect) {
        super(effect);
    }

    @Override
    public ComplyCantCastEffect copy() {
        return new ComplyCantCastEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
            return "You may not cast a card named " + cardName + " (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }
}
