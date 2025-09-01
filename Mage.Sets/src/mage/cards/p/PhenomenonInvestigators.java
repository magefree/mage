package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HorrorEnchantmentCreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class PhenomenonInvestigators extends CardImpl {

    public PhenomenonInvestigators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As Phenomenon Investigators enters, choose Believe or Doubt.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.BELIEVE, ModeChoice.DOUBT)));

        // * Believe -- Whenever a nontoken creature you control dies, create a 2/2 black Horror enchantment creature token.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new DiesCreatureTriggeredAbility(
                        new CreateTokenEffect(new HorrorEnchantmentCreatureToken()),
                        false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
                ), ModeChoice.BELIEVE
        )));

        // * Doubt -- At the beginning of your end step, you may return a nonland permanent you own to your hand. If you do, draw a card.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new PhenomenonInvestigatorsReturnCost()
                )), ModeChoice.DOUBT
        )));
    }

    private PhenomenonInvestigators(final PhenomenonInvestigators card) {
        super(card);
    }

    @Override
    public PhenomenonInvestigators copy() {
        return new PhenomenonInvestigators(this);
    }
}

class PhenomenonInvestigatorsReturnCost extends CostImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("a nonland permanent you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    PhenomenonInvestigatorsReturnCost() {
        this.addTarget(new TargetPermanent(filter).withNotTarget(true));
        text = "return a nonland permanent you own to your hand";
    }

    private PhenomenonInvestigatorsReturnCost(final PhenomenonInvestigatorsReturnCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (this.getTargets().choose(Outcome.ReturnToHand, controllerId, source.getSourceId(), source, game)) {
                Permanent permanentToReturn = game.getPermanent(this.getTargets().getFirstTarget());
                if (permanentToReturn == null) {
                    return false;
                }
                controller.moveCards(permanentToReturn, Zone.HAND, ability, game);
                paid = true;
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return canChooseOrAlreadyChosen(ability, source, controllerId, game);
    }

    @Override
    public PhenomenonInvestigatorsReturnCost copy() {
        return new PhenomenonInvestigatorsReturnCost(this);
    }
}
