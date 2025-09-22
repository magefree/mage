package mage.cards.p;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Pyrohemia extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreaturePermanent("no creatures are on the battlefield"), ComparisonType.EQUAL_TO, 0, false
    );

    public Pyrohemia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // At the beginning of the end step, if no creatures are on the battlefield, sacrifice Pyrohemia.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false, condition
        ));

        // {R}: Pyrohemia deals 1 damage to each creature and each player.
        this.addAbility(new SimpleActivatedAbility(new DamageEverythingEffect(1), new ManaCostsImpl<>("{R}")));
    }

    private Pyrohemia(final Pyrohemia card) {
        super(card);
    }

    @Override
    public Pyrohemia copy() {
        return new Pyrohemia(this);
    }
}
