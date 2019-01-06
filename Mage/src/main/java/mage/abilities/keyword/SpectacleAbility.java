
package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class SpectacleAbility extends SpellAbility {

    public static final String SPECTACLE_ACTIVATION_VALUE_KEY = "spectacleActivation";

    private String rule;

    public SpectacleAbility(Card card, ManaCost spectacleCosts) {
        super(spectacleCosts, card.getName() + " with spectacle", Zone.HAND, SpellAbilityType.BASE_ALTERNATE);
        this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
        this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
        this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = card.getSpellAbility().getTiming();
        this.setRuleAtTheTop(true);
        this.rule = "Spectacle " + spectacleCosts.getText()
                + " <i>(You may cast this spell for its spectacle cost rather than its mana cost if an opponent lost life this turn.)</i>";
    }

    public SpectacleAbility(final SpectacleAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null && watcher.getAllOppLifeLost(playerId, game) > 0) {
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            ArrayList<Integer> spectacleActivations = (ArrayList) game.getState().getValue(SPECTACLE_ACTIVATION_VALUE_KEY + getSourceId());
            if (spectacleActivations == null) {
                spectacleActivations = new ArrayList<>(); // zoneChangeCounter
                game.getState().setValue(SPECTACLE_ACTIVATION_VALUE_KEY + getSourceId(), spectacleActivations);
            }
            spectacleActivations.add(game.getState().getZoneChangeCounter(getSourceId()));
            return true;
        }
        return false;
    }

    @Override
    public SpectacleAbility copy() {
        return new SpectacleAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return rule;
    }

}
