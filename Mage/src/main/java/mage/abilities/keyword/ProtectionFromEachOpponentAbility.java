package mage.abilities.keyword;

import mage.MageItem;
import mage.MageObject;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class ProtectionFromEachOpponentAbility extends ProtectionAbility {

    public ProtectionFromEachOpponentAbility() {
        super(StaticFilters.FILTER_CARD);
    }

    private ProtectionFromEachOpponentAbility(final ProtectionFromEachOpponentAbility ability) {
        super(ability);
    }

    @Override
    public ProtectionFromEachOpponentAbility copy() {
        return new ProtectionFromEachOpponentAbility(this);
    }

    @Override
    public String getRule() {
        return "protection from each of your opponents";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        return Optional
                .ofNullable(source)
                .map(MageItem::getId)
                .map(game::getControllerId)
                .map(uuid -> !game.getOpponents(this.getSourceId()).contains(uuid))
                .orElse(true);
    }
}
