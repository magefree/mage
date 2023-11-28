package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class SurgeAbility extends SpellAbility {

    public static final String SURGE_ACTIVATION_VALUE_KEY = "surgeActivation";

    private final String rule;

    public SurgeAbility(Card card, String surgeCosts) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with surge");
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(surgeCosts));

        this.setRuleAtTheTop(true);
        this.rule = "Surge " + surgeCosts
                + " <i>(You may cast this spell for its surge cost if you or a teammate has cast another spell this turn.)</i>";
    }

    protected SurgeAbility(final SurgeAbility ability) {
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
                        if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(playerToCheckId) > 0
                                && super.canActivate(playerId, game).canActivate()) {
                            return new ActivationStatus(new ApprovingObject(this, game));
                        }
                    }
                }
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            this.setCostsTag(SURGE_ACTIVATION_VALUE_KEY, null);
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
