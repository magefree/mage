package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.cards.Card;
import mage.constants.SpellAbilityCastMode;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

public class DisturbAbility extends SpellAbility {

    public DisturbAbility(Cost cost, TimingRule timingRule) {
        super(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.DISTURB);
        this.setAdditionalCostsRuleVisible(false);
        this.name = "Disturb " + cost.getText();
        this.addCost(cost);
        this.timing = timingRule;
    }

    private DisturbAbility(final DisturbAbility ability) {
        super(ability);
    }

    @Override
    public DisturbAbility copy() {
        return new DisturbAbility(this);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getSourceId(), Boolean.TRUE);
            return true;
        }
        return false;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Disturb");
        if (!costs.isEmpty()) {
            sbRule.append("&mdash;");
        } else {
            sbRule.append(' ');
        }
        if (!manaCosts.isEmpty()) {
            sbRule.append(manaCosts.getText());
        }
        if (!costs.isEmpty()) {
            if (!manaCosts.isEmpty()) {
                sbRule.append(", ");
            }
            sbRule.append(costs.getText());
            sbRule.append('.');
        }
        sbRule.append(" <i>(You may cast this card transformed from your graveyard for its disturb cost.)</i>");
        return sbRule.toString();
    }
}
