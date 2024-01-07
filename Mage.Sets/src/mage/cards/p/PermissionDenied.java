package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.constants.*;
import mage.target.TargetSpell;
import mage.constants.Outcome;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.MageObject;

/**
 *
 * @author skaspels
 */
public final class PermissionDenied extends CardImpl {

    public PermissionDenied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");


        // Counter target noncreature spell.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        // Your opponents can't cast noncreature spells this turn.
        this.getSpellAbility().addEffect(new PermissionDeniedOpponentEffect());
    }

    private PermissionDenied(final PermissionDenied card) {
        super(card);
    }

    @Override
    public PermissionDenied copy() {
        return new PermissionDenied(this);
    }
}

class PermissionDeniedOpponentEffect extends ContinuousRuleModifyingEffectImpl {

    public PermissionDeniedOpponentEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Your opponents can't cast noncreature spells this turn.";
    }

    private PermissionDeniedOpponentEffect(final PermissionDeniedOpponentEffect effect) {
        super(effect);
    }

    @Override
    public PermissionDeniedOpponentEffect copy() {
        return new PermissionDeniedOpponentEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast noncreature spells this turn (" + mageObject.getIdName() + ").";
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