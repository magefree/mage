
package mage.cards.h;

import java.util.Optional;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public final class HandToHand extends CardImpl {

    public HandToHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // During combat, players can't cast instant spells or activate abilities that aren't mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HandToHandEffect()));
    }

    private HandToHand(final HandToHand card) {
        super(card);
    }

    @Override
    public HandToHand copy() {
        return new HandToHand(this);
    }
}

class HandToHandEffect extends ContinuousRuleModifyingEffectImpl {

    public HandToHandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "During combat, players can't cast instant spells or activate abilities that aren't mana abilities";
    }

    public HandToHandEffect(final HandToHandEffect effect) {
        super(effect);
    }

    @Override
    public HandToHandEffect copy() {
        return new HandToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "During combat, players can't cast instant spells or activate abilities that aren't mana abilities (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnPhaseType() == TurnPhase.COMBAT) {
            MageObject object = game.getObject(event.getSourceId());
            if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                if (object != null && object.isInstant(game)) {
                    return true;
                }
            }
            if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
                Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
                if (ability.isPresent() && !(ability.get() instanceof ActivatedManaAbilityImpl)) {
                    return true;
                }
            }
        }
        return false;
    }
}
