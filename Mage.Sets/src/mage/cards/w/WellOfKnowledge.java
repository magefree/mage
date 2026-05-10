package mage.cards.w;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class WellOfKnowledge extends CardImpl {

    public WellOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}: Draw a card. Any player may activate this ability but only during their draw step.
        this.addAbility(new WellOfKnowledgeActivatedAbility());

    }

    private WellOfKnowledge(final WellOfKnowledge card) {
        super(card);
    }

    @Override
    public WellOfKnowledge copy() {
        return new WellOfKnowledge(this);
    }
}

class WellOfKnowledgeActivatedAbility extends ActivatedAbilityImpl {

    WellOfKnowledgeActivatedAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
    }

    private WellOfKnowledgeActivatedAbility(final WellOfKnowledgeActivatedAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // note: does not call the super, because needs to be both more and less permissive
        if (game.getTurnStepType() == PhaseStep.DRAW
                && game.isActivePlayer(playerId)
                && getCosts().canPay(this, this, playerId, game)) {
            return ActivationStatus.withoutApprovingObject(true);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public WellOfKnowledgeActivatedAbility copy() {
        return new WellOfKnowledgeActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "{2}: Draw a card. Any player may activate this ability but only during their draw step.";
    }
}
