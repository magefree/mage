package mage.cards.w;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class WellOfKnowledge extends CardImpl {

    public WellOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}: Draw a card. Any player may activate this ability but only during their draw step.
        this.addAbility(new WellOfKnowledgeConditionalActivatedAbility());

    }

    private WellOfKnowledge(final WellOfKnowledge card) {
        super(card);
    }

    @Override
    public WellOfKnowledge copy() {
        return new WellOfKnowledge(this);
    }
}

class WellOfKnowledgeConditionalActivatedAbility extends ActivatedAbilityImpl {

    public WellOfKnowledgeConditionalActivatedAbility() {
        super(Zone.BATTLEFIELD, new WellOfKnowledgeEffect(), new GenericManaCost(2));
        condition = new IsStepCondition(PhaseStep.DRAW, false);
    }

    private WellOfKnowledgeConditionalActivatedAbility(final WellOfKnowledgeConditionalActivatedAbility ability) {
        super(ability);
        this.condition = ability.condition;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return new Effects();
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (condition.apply(game, this)
                && getCosts().canPay(this, this, playerId, game)
                && game.isActivePlayer(playerId)) {
            this.activatorId = playerId;
            return new ActivationStatus(new ApprovingObject(this, game));
        }
        return ActivationStatus.getFalse();

    }

    @Override
    public WellOfKnowledgeConditionalActivatedAbility copy() {
        return new WellOfKnowledgeConditionalActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "{2}: Draw a card. Any player may activate this ability but only during their draw step.";
    }
}

class WellOfKnowledgeEffect extends OneShotEffect {

    public WellOfKnowledgeEffect() {
        super(Outcome.DrawCard);
    }

    private WellOfKnowledgeEffect(final WellOfKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public WellOfKnowledgeEffect copy() {
        return new WellOfKnowledgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof ActivatedAbilityImpl) {
            Player activator = game.getPlayer(((ActivatedAbilityImpl) source).getActivatorId());
            if (activator != null) {
                activator.drawCards(1, source, game);
                return true;
            }

        }
        return false;
    }
}
