package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author choiseul11
 */
public final class MurderousBetrayal extends CardImpl {

    public MurderousBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}{B}");

        // {B}{B}, Pay half your life, rounded up: Destroy target nonblack creature. It can't be regenerated.
        Effect effect = new DestroyTargetEffect(true);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new MurderousBetrayalCost());
        ability.addCost(new ManaCostsImpl<>("{B}{B}"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability);
    }

    private MurderousBetrayal(final MurderousBetrayal card) {
        super(card);
    }

    @Override
    public MurderousBetrayal copy() {
        return new MurderousBetrayal(this);
    }
}

class MurderousBetrayalCost extends CostImpl {

    MurderousBetrayalCost() {
        this.text = "Pay half your life, rounded up";
    }

    MurderousBetrayalCost(MurderousBetrayalCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && (controller.getLife() < 1 || controller.canPayLifeCost(ability));
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }

        int currentLife = controller.getLife();
        int lifeToPay = Math.max(0, (currentLife + currentLife % 2) / 2); // Divide by two and round up.
        if (lifeToPay <= 0) {
            this.paid = true;
        } else {
            this.paid = CardUtil.tryPayLife(lifeToPay, controller, source, game);
        }
        return this.paid;
    }

    @Override
    public MurderousBetrayalCost copy() {
        return new MurderousBetrayalCost(this);
    }
}
