package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattlePlan extends CardImpl {

    public BattlePlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // At the beginning of combat on your turn, target creature you control gets +2/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 0), TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Basic landcycling {1}{R}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    private BattlePlan(final BattlePlan card) {
        super(card);
    }

    @Override
    public BattlePlan copy() {
        return new BattlePlan(this);
    }
}
