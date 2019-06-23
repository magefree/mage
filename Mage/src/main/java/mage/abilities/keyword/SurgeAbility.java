package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class SurgeAbility extends SpellAbility {

    public static final String SURGE_ACTIVATION_VALUE_KEY = "surgeActivation";

    private String rule;

    public SurgeAbility(Card card, String surgeCosts) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with surge");
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();
        this.addManaCost(new ManaCostsImpl<>(surgeCosts));

        this.setRuleAtTheTop(true);
        this.rule = "Surge " + surgeCosts
                + " <i>(You may cast this spell for its surge cost if you or a teammate has cast another spell this turn.)</i>";
    }

    public SurgeAbility(final SurgeAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // check if controller or teammate has already cast a spell this turn
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID playerToCheckId : game.getState().getPlayersInRange(playerId, game)) {
                    if (!player.hasOpponent(playerToCheckId, game)) {
                        if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(playerToCheckId) > 0) {
                            return super.canActivate(playerId, game);
                        }
                    }
                }
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            ArrayList<Integer> surgeActivations = (ArrayList) game.getState().getValue(SURGE_ACTIVATION_VALUE_KEY + getSourceId());
            if (surgeActivations == null) {
                surgeActivations = new ArrayList<>(); // zoneChangeCounter
                game.getState().setValue(SURGE_ACTIVATION_VALUE_KEY + getSourceId(), surgeActivations);
            }
            surgeActivations.add(game.getState().getZoneChangeCounter(getSourceId()));
            return true;
        }
        return false;
    }

    @Override
    public SurgeAbility copy() {
        return new SurgeAbility(this);
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
