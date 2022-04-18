package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class CasualtyAbility extends StaticAbility {

    public CasualtyAbility(Card card, int number) {
        super(Zone.ALL, new InfoEffect(
                "Casualty " + number + " <i>(As you cast this spell, " +
                        "you may sacrifice a creature with power " + number +
                        " or greater. When you do, copy this spell.)</i>"
        ));
        card.getSpellAbility().addCost(new CasualtyCost(number));
        this.setRuleAtTheTop(true);
    }

    private CasualtyAbility(final CasualtyAbility ability) {
        super(ability);
    }

    @Override
    public CasualtyAbility copy() {
        return new CasualtyAbility(this);
    }
}

class CasualtyCost extends SacrificeTargetCost {

    CasualtyCost(int number) {
        super(new TargetControlledPermanent(0, 1, makeFilter(number), true));
        this.text = "";
    }

    private CasualtyCost(final CasualtyCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!super.pay(ability, game, source, controllerId, noMana, costToPay)) {
            return false;
        }
        if (!getPermanents().isEmpty()) {
            game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                    new CopySourceSpellEffect(), false, "when you do, copy this spell"
            ), source);
        }
        return true;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public CasualtyCost copy() {
        return new CasualtyCost(this);
    }

    private static FilterControlledPermanent makeFilter(int number) {
        FilterControlledPermanent filter = new FilterControlledCreaturePermanent(
                "creature with power " + number + " or greater"
        );
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, number - 1));
        return filter;
    }
}
