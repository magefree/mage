
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class PsychicVortex extends CardImpl {

    public PsychicVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");

        // Cumulative upkeep-Draw a card.
        this.addAbility(new CumulativeUpkeepAbility(new PsychicVortexCost()));
        
        // At the beginning of your end step, sacrifice a land and discard your hand.
        Effect effect = new SacrificeTargetEffect();
        effect.setText("sacrifice a land");
        Ability ability = new BeginningOfEndStepTriggeredAbility(effect, TargetController.YOU, false);
        effect = new DiscardHandControllerEffect();
        effect.setText("and discard your hand");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    public PsychicVortex(final PsychicVortex card) {
        super(card);
    }

    @Override
    public PsychicVortex copy() {
        return new PsychicVortex(this);
    }
}

class PsychicVortexCost extends CostImpl {
    
    PsychicVortexCost() {
        this.text = "Draw a card.";
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            controller.drawCards(1, game);
            this.paid = true;
            return true;
            }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && controller.getLibrary().hasCards();
    }
    
    @Override
    public PsychicVortexCost copy() {
        return new PsychicVortexCost();
    }
}