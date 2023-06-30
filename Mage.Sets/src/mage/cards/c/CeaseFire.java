
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth (Steel Golem), cbt33
 */
public final class CeaseFire extends CardImpl {

    public CeaseFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Target player can't cast creature spells this turn.
        this.getSpellAbility().addEffect(new CeaseFireEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CeaseFire(final CeaseFire card) {
        super(card);
    }

    @Override
    public CeaseFire copy() {
        return new CeaseFire(this);
    }
}

class CeaseFireEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell();

    public CeaseFireEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't cast creature spells this turn";
    }

    public CeaseFireEffect(final CeaseFireEffect effect) {
        super(effect);
    }

    @Override
    public CeaseFireEffect copy() {
        return new CeaseFireEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast creature spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(getTargetPointer().getFirst(game, source))) {
            Spell spell = game.getStack().getSpell(event.getSourceId());
            if (filter.match(spell, game)) {
                return true;
            }
        }
        return false;
    }

}
