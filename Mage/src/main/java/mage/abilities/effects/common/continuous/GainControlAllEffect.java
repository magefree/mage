/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fenhl
 */
public class GainControlAllEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;
    private final UUID controllingPlayerId;

    public GainControlAllEffect(Duration duration, FilterPermanent filter) {
        this(duration, filter, null);
    }

    public GainControlAllEffect(Duration duration, FilterPermanent filter, UUID controllingPlayerId) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.filter = filter;
        this.controllingPlayerId = controllingPlayerId;
    }

    public GainControlAllEffect(final GainControlAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.controllingPlayerId = effect.controllingPlayerId;
    }

    @Override
    public GainControlAllEffect copy() {
        return new GainControlAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent != null) {
                if (controllingPlayerId == null) {
                    permanent.changeControllerId(source.getControllerId(), game);
                } else {
                    permanent.changeControllerId(controllingPlayerId, game);
                }

            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gain control of ").append(filter.getMessage());
        return sb.toString();
    }
}
