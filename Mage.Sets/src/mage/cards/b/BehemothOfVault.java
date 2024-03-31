package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class BehemothOfVault extends CardImpl {

    public BehemothOfVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Behemoth of Vault 0 enters the battlefield, you get {E}{E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(4)));

        // When Behemoth of Vault 0 dies, you may pay an amount of {E} equal to target nonland permanent's mana value. When you do, destroy that permanent.
        ReflexiveTriggeredAbility reflexiveTrigger = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        Ability ability = new DiesSourceTriggeredAbility(new DoWhenCostPaid(reflexiveTrigger, new BehemothOfVaultCost(),
                "pay an amount of {E} equal to target nonland permanent's mana value"));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private BehemothOfVault(final BehemothOfVault card) {
        super(card);
    }

    @Override
    public BehemothOfVault copy() {
        return new BehemothOfVault(this);
    }
}

//Based on PayEnergyCost
class BehemothOfVaultCost extends CostImpl {
    BehemothOfVaultCost() {
        this.text = "pay an amount of {E} equal to target nonland permanent's mana value";
    }

    BehemothOfVaultCost(BehemothOfVaultCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent != null && player != null) {
            int amount = targetPermanent.getManaValue();
            StringBuilder sb = new StringBuilder("pay ");
            for (int i = 0; i < amount; i++) {
                sb.append("{E}");
            }
            this.text = sb.toString();
            return player.getCounters().getCount(CounterType.ENERGY) >= amount;
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent != null && player != null && player.getCounters().getCount(CounterType.ENERGY) >= targetPermanent.getManaValue()) {
            player.getCounters().removeCounter(CounterType.ENERGY, targetPermanent.getManaValue());
            paid = true;
        }
        return paid;
    }

    @Override
    public BehemothOfVaultCost copy() {
        return new BehemothOfVaultCost(this);
    }
}
