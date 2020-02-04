/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author Styxo
 */
public class ActivateAbilitiesAnyTimeYouCouldCastInstantEffect extends AsThoughEffectImpl {

    private Class activatedAbility;

    public ActivateAbilitiesAnyTimeYouCouldCastInstantEffect(Class activatedAbility, String activatedAbilityName) {
        super(AsThoughEffectType.ACTIVATE_AS_INSTANT, Duration.EndOfGame, Outcome.Benefit);
        this.activatedAbility = activatedAbility;
        staticText = "You may activate " + activatedAbilityName + " any time you could cast an instant";
    }

    public ActivateAbilitiesAnyTimeYouCouldCastInstantEffect(final ActivateAbilitiesAnyTimeYouCouldCastInstantEffect effect) {
        super(effect);
        this.activatedAbility = effect.activatedAbility;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ActivateAbilitiesAnyTimeYouCouldCastInstantEffect copy() {
        return new ActivateAbilitiesAnyTimeYouCouldCastInstantEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return affectedAbility.isControlledBy(source.getControllerId())
                && activatedAbility.isInstance(affectedAbility);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return false; // Not used
    }

}
