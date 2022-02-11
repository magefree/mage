package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PsychicVortex extends CardImpl {

    public PsychicVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Cumulative upkeep-Draw a card.
        this.addAbility(new CumulativeUpkeepAbility(new PsychicVortexCost()));

        // At the beginning of your end step, sacrifice a land and discard your hand.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new SacrificeControllerEffect(
                StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT, 1, null
        ), TargetController.YOU, false);
        ability.addEffect(new DiscardHandControllerEffect().concatBy("and"));
        this.addAbility(ability);
    }

    private PsychicVortex(final PsychicVortex card) {
        super(card);
    }

    @Override
    public PsychicVortex copy() {
        return new PsychicVortex(this);
    }
}

class PsychicVortexCost extends CostImpl {

    PsychicVortexCost() {
        this.text = "Draw a card";
    }

    private PsychicVortexCost(final PsychicVortexCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            controller.drawCards(1, source, game);
            this.paid = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null;
    }

    @Override
    public PsychicVortexCost copy() {
        return new PsychicVortexCost(this);
    }
}
