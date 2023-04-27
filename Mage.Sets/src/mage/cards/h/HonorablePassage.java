package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class HonorablePassage extends CardImpl {

    public HonorablePassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // The next time a source of your choice would deal damage to any target this turn, prevent that damage. If damage from a red source is prevented this way, Honorable Passage deals that much damage to the source's controller.
        this.getSpellAbility().addEffect(new HonorablePassageEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private HonorablePassage(final HonorablePassage card) {
        super(card);
    }

    @Override
    public HonorablePassage copy() {
        return new HonorablePassage(this);
    }
}

class HonorablePassageEffect extends PreventNextDamageFromChosenSourceToTargetEffect {

    public HonorablePassageEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "The next time a source of your choice would deal damage to any target this turn, prevent that damage. If damage from a red source is prevented this way, {this} deals that much damage to the source's controller";
    }

    public HonorablePassageEffect(final HonorablePassageEffect effect) {
        super(effect);
    }

    @Override
    public HonorablePassageEffect copy() {
        return new HonorablePassageEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        PreventionEffectData preventEffectData = preventDamageAction(event, source, game);
        if (preventEffectData.getPreventedDamage() > 0) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.getColor(game).isRed()) {
                UUID sourceControllerId = game.getControllerId(event.getSourceId());
                if (sourceControllerId != null) {
                    Player sourceController = game.getPlayer(sourceControllerId);
                    if (sourceController != null) {
                        sourceController.damage(damage, source.getSourceId(), source, game);
                    }
                }
            }
            this.used = true;
        }
        return false;
    }
}
