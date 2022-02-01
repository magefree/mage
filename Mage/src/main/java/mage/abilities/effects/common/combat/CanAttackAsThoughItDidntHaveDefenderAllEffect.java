package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Quercitron
 */
public class CanAttackAsThoughItDidntHaveDefenderAllEffect extends AsThoughEffectImpl {

    private final FilterPermanent filter;

    public CanAttackAsThoughItDidntHaveDefenderAllEffect(Duration duration, FilterPermanent filter) {
        super(AsThoughEffectType.ATTACK, duration, Outcome.Benefit);
        this.filter = filter;
        this.staticText = getText();
    }

    private CanAttackAsThoughItDidntHaveDefenderAllEffect(final CanAttackAsThoughItDidntHaveDefenderAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public CanAttackAsThoughItDidntHaveDefenderAllEffect copy() {
        return new CanAttackAsThoughItDidntHaveDefenderAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Permanent permanent = game.getPermanent(objectId);
        return filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
    }

    private String getText() {
        StringBuilder sb = new StringBuilder(filter.getMessage());
        sb.append(" can attack ");
        if (!duration.toString().isEmpty()) {
            if (Duration.EndOfTurn == duration) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
            sb.append(' ');
        }
        sb.append("as though they didn't have defender");
        return sb.toString();
    }
}
