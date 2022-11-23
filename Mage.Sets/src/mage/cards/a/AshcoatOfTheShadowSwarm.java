package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class AshcoatOfTheShadowSwarm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.RAT, "Rats");
    private static final FilterCreatureCard filter2 = new FilterCreatureCard("Rat creature cards");

    static {
        filter2.add(SubType.ZOMBIE.getPredicate());
    }

    public AshcoatOfTheShadowSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT, SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Ashcoat of the Shadow Swarm attacks or blocks, other Rats you control
        // get +X/+X until end of turn, where X is the number of Rats you control.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BoostControlledEffect(
                xValue, xValue, Duration.EndOfTurn, filter, true
        ).setText("other Rats you control get +X/+X until end of turn, where X is the number of Rats you control"), false));

        // At the beginning of your end step, you may mill four cards. If you do,
        // return up to two Rat creature cards from your graveyard to your hand.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new ReturnFromGraveyardToHandTargetEffect(),
                new AshcoatCost()
        ), TargetController.YOU, false);
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, filter2));
        this.addAbility(ability);
    }

    private AshcoatOfTheShadowSwarm(final AshcoatOfTheShadowSwarm card) {
        super(card);
    }

    @Override
    public AshcoatOfTheShadowSwarm copy() {
        return new AshcoatOfTheShadowSwarm(this);
    }
}

class AshcoatCost extends CostImpl {

    public AshcoatCost() {
        this.text = "mill four cards";
    }

    public AshcoatCost(AshcoatCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            player.millCards(4, source, game);
            paid = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getPlayer(controllerId) != null;
    }

    @Override
    public AshcoatCost copy() {
        return new AshcoatCost(this);
    }
}
