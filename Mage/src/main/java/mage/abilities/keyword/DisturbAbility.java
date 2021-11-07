package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.MageObjectAttribute;

import java.util.UUID;

/**
 * @author weirddan455
 */
public class DisturbAbility extends SpellAbility {

    public DisturbAbility(Cost cost) {
        this(cost, TimingRule.SORCERY);
    }

    public DisturbAbility(Cost cost, TimingRule timingRule) {
        super(null, "", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE, SpellAbilityCastMode.DISTURB);
        this.setAdditionalCostsRuleVisible(false);
        this.name = "Disturb " + cost.getText();
        this.addCost(cost);
        this.timing = timingRule;
        this.addSubAbility(new TransformAbility());
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
            game.addEffect(new DisturbEffect(), this);
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

class DisturbEffect extends ContinuousEffectImpl {

    public DisturbEffect() {
        super(Duration.WhileOnStack, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.BecomeCreature);
        staticText = "";
    }

    private DisturbEffect(final DisturbEffect effect) {
        super(effect);
    }

    @Override
    public DisturbEffect copy() {
        return new DisturbEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            Card secondFace = card.getSecondCardFace();
            if (secondFace != null) {
                MageObjectAttribute moa = game.getState().getCreateMageObjectAttribute(card, game);
                moa.getCardType().clear();
                moa.getCardType().addAll(secondFace.getCardType());
                moa.getColor().setColor(secondFace.getColor(game));
                moa.getSubtype().copyFrom(secondFace.getSubtype());
                game.getState().getCardState(card.getId()).clearAbilities();
                for (Ability ability : secondFace.getAbilities()) {
                    game.getState().addOtherAbility(card, ability);
                }
                return true;
            }
        }
        return false;
    }
}
