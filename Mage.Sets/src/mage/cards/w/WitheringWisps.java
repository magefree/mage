package mage.cards.w;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author L_J
 */
public final class WitheringWisps extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreaturePermanent("no creatures are on the battlefield"),
            ComparisonType.EQUAL_TO, 0, false
    );

    public WitheringWisps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // At the beginning of the end step, if no creatures are on the battlefield, sacrifice Withering Wisps.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.NEXT, new SacrificeSourceEffect(), false, condition));

        // {B}: Withering Wisps deals 1 damage to each creature and each player. Activate this ability no more times each turn than the number of snow Swamps you control.
        this.addAbility(new WitheringWispsActivatedAbility());
    }

    private WitheringWisps(final WitheringWisps card) {
        super(card);
    }

    @Override
    public WitheringWisps copy() {
        return new WitheringWisps(this);
    }
}

class WitheringWispsActivatedAbility extends ActivatedAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP, "snow Swamps you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    @Override
    public int getMaxActivationsPerTurn(Game game) {
        return game.getBattlefield().getActivePermanents(filter, getControllerId(), this, game).size();
    }

    public WitheringWispsActivatedAbility() {
        super(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl<>("{B}"));

    }

    private WitheringWispsActivatedAbility(final WitheringWispsActivatedAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate no more times each turn than the number of snow Swamps you control.";
    }

    @Override
    public WitheringWispsActivatedAbility copy() {
        return new WitheringWispsActivatedAbility(this);
    }
}
